package com.formanova.ratingservice.api.messaging;

import com.formanova.common.Event;
import com.formanova.common.dto.ReviewDto;
import com.formanova.ratingservice.api.mapper.ReviewMapper;
import com.formanova.ratingservice.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@Profile("docker")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class MessageProcessorConfig {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    @Bean
    public Consumer<Message<Event<Long, ? extends ReviewDto>>> messageProcessor() {
        return message -> {
            Event<Long, ? extends ReviewDto> event = message.getPayload();

            /*
            To ensure that we can propagate exceptions back to the messaging system,
            we call the block() method on the responses we get back from the reviewService bean.
            This ensures that the message processor waits for the reviewService bean to complete its creation
            or deletion in the underlying database. Without calling the block() method, we would not be able
            to propagate exceptions and the messaging system would not be able to re-queue a failed attempt or
            possibly move the message to a dead-letter queue; instead, the message would silently be dropped
             */

            Long reviewId = event.getKey();
            Long targetId = (Long) message.getHeaders().get("targetId");

            switch (event.getEventType()) {
                case CREATE -> {
                    ReviewDto reviewDto = event.getData();
                    log.debug("Adding review: {}", reviewDto);
                    reviewService.save(reviewMapper.toEntity(reviewDto, null, targetId))
                            .block();
                }
                case DELETE -> {
                    if (reviewId != null) {
                        log.debug("Deleting review with id: {}", reviewId);
                        reviewService.deleteById(reviewId)
                                .block();
                    } else if (targetId != null) {
                        log.debug("Deleting all reviews with targetId: {}", targetId);
                        reviewService.deleteAllByTargetId(targetId)
                                .block();
                    }
                }
                default -> {
                    String errorMessage = "Incorrect event type: " + event.getEventType();
                    log.warn(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            }
        };
    }

}
