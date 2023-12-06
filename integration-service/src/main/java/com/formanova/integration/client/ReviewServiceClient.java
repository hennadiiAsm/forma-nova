package com.formanova.integration.client;

import com.formanova.common.Event;
import com.formanova.common.dto.ReviewDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Service
@Slf4j
public class ReviewServiceClient {

    private static final String BINDING_NAME = "ratingService-out-0";

    private final WebClient webClient;

    private final StreamBridge streamBridge;

    private final String ratingServiceUrl;

    ReviewServiceClient(WebClient webClient,
                        StreamBridge streamBridge,

                        @Value("${network.rating-service.scheme}") String ratingServiceScheme,
                        @Value("${network.rating-service.domain}") String ratingServiceDomain,
                        @Value("${network.rating-service.port}") String ratingServicePort) {

        this.webClient = webClient;
        this.streamBridge = streamBridge;

        this.ratingServiceUrl = ratingServiceScheme + "://" + ratingServiceDomain + ":" + ratingServicePort;
    }

    public Flux<ReviewDto> getReviewsByTargetId(long targetId) {
        return webClient.get().uri(ratingServiceUrl + "/reviews")
                .attribute("targetId", targetId)
                .retrieve()
                .bodyToFlux(ReviewDto.class)
                .doOnNext(review -> log.debug("Received " + review))
                .onErrorResume(ex -> Flux.empty());
    }

    public Mono<Void> deleteReviewsByTargetId(Long targetId) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.DELETE, null, null);
            sendMessage(event, Map.of("targetId", targetId));
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    private void sendMessage(Event<?, ?> event, Map<String, ?> headers) {
        log.debug("Sending a {} message to {}", event.getEventType(), BINDING_NAME);

        var messageBuilder = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey());
        for (var entry : headers.entrySet()) {
            messageBuilder.setHeader(entry.getKey(), entry.getValue());
        }
        streamBridge.send(BINDING_NAME, messageBuilder.build());
    }
}
