package com.formanova.user.api.messaging;

import com.formanova.common.Event;
import com.formanova.common.dto.user.UserPublicDto;
import com.formanova.common.dto.user.UserRegistrationDto;
import com.formanova.user.api.mapper.UserMapper;
import com.formanova.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Consumer;

@Configuration
@Profile("docker")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class MessageProcessorConfig {

    private final UserService userService;

    private final UserMapper userMapper;

    @Bean
    public Consumer<Event<?, ? extends UserPublicDto>> messageProcessor() {
        return event -> {
            log.debug("Process message created at {}...", event.getEventCreatedAt());

            /*
            To ensure that we can propagate exceptions back to the messaging system,
            we call the block() method on the responses we get back from the userService bean.
            This ensures that the message processor waits for the userService bean to complete its creation
            or deletion in the underlying database. Without calling the block() method, we would not be able
            to propagate exceptions and the messaging system would not be able to re-queue a failed attempt or
            possibly move the message to a dead-letter queue; instead, the message would silently be dropped
             */
            switch (event.getEventType()) {
                case CREATE -> {
                    UserRegistrationDto userDto = (UserRegistrationDto) event.getData();
                    log.debug("Save user: {}", userDto);
                    userService.saveUser(userMapper.toEntity(userDto))
                            .block();
                }
                case DELETE -> {
                    Long userId = (Long) event.getKey();
                    log.debug("Delete user with id: {}", userId);
                    userService.deleteUserById(userId)
                            .block();
                }
                default -> {
                    String errorMessage = "Incorrect event type: " + event.getEventType();
                    log.warn(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            }

            log.debug("Message processing done!");
        };
    }

}
