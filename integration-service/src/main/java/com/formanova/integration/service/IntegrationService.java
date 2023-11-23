package com.formanova.integration.service;

import com.formanova.common.Event;
import com.formanova.common.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class IntegrationService {

    private final WebClient webClient;

    private final StreamBridge streamBridge;

    IntegrationService(WebClient.Builder webClientBuilder, StreamBridge streamBridge) {
        this.webClient = webClientBuilder.build();
        this.streamBridge = streamBridge;
    }

    public Mono<ResponseEntity<UserDto>> getUserById(Long id) {

        return webClient.get().uri("http://localhost:8080/users/{id}", id)
                .retrieve()
                .toEntity(UserDto.class);
    }

    public Mono<Void> saveUser(UserDto userDto) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.CREATE, null, userDto);
            sendMessage("users-out-0", event);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    private void sendMessage(String bindingName, Event<?, ?> event) {
        log.debug("Sending a {} message to {}", event.getEventType(), bindingName);
        var message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();
        streamBridge.send(bindingName, message);
    }

    public Mono<Health> getUserServiceHealth() {
        String uri = "http://localhost:8080/actuator/health";
        return webClient.get().uri(uri).retrieve().bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()));
    }
}
