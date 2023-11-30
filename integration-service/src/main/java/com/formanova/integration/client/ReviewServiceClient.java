package com.formanova.integration.client;

import com.formanova.common.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ReviewServiceClient {

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
                .onErrorResume(ex -> Flux.empty());
    }
}
