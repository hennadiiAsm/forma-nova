package com.formanova.integration.client;

import com.formanova.common.Event;
import com.formanova.common.dto.user.UserPublicDto;
import com.formanova.common.dto.user.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class UserServiceClient {

    private final WebClient webClient;

    private final StreamBridge streamBridge;

    private final String userServiceUrl;

    UserServiceClient(WebClient.Builder webClientBuilder,
                      StreamBridge streamBridge,

                      @Value("${network.user-service.scheme}") String userServiceScheme,
                      @Value("${network.user-service.domain}") String userServiceDomain,
                      @Value("${network.user-service.port}") String userServicePort) {

        this.webClient = webClientBuilder.build();
        this.streamBridge = streamBridge;

        this.userServiceUrl = userServiceScheme + "://" + userServiceDomain + ":" + userServicePort;
    }

    public Mono<ResponseEntity<UserPublicDto>> getUserById(Long id) {

        return webClient.get().uri(userServiceUrl + "/users/{id}", id)
                .retrieve()
                .toEntity(UserPublicDto.class);
    }

    public Mono<Void> createUser(UserRegistrationDto userDto) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.CREATE, null, userDto);
            sendMessage("users-out-0", event);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    public Mono<Void> deleteUserById(Long id) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.DELETE, id, null);
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
        String uri = userServiceUrl +  "/actuator/health";
        return webClient.get().uri(uri).retrieve().bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()));
    }
}