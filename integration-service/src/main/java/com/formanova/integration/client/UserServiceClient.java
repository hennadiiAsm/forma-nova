package com.formanova.integration.client;

import com.formanova.common.Event;
import com.formanova.common.dto.user.UserPrivateDto;
import com.formanova.common.dto.user.UserRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class UserServiceClient {

    private static final String BINDING_NAME = "userService-out-0";

    private final WebClient webClient;

    private final StreamBridge streamBridge;

    private final String userServiceUrl;

    UserServiceClient(WebClient webClient,
                      StreamBridge streamBridge,

                      @Value("${network.user-service.scheme}") String userServiceScheme,
                      @Value("${network.user-service.domain}") String userServiceDomain,
                      @Value("${network.user-service.port}") String userServicePort) {

        this.webClient = webClient;
        this.streamBridge = streamBridge;

        this.userServiceUrl = userServiceScheme + "://" + userServiceDomain + ":" + userServicePort;
    }

    public Mono<UserPrivateDto> getUserById(Long id) {

        return webClient.get().uri(userServiceUrl + "/users/{id}", id)
                .retrieve()
                .bodyToMono(UserPrivateDto.class)
                .doOnNext(user -> log.debug("Received " + user));
    }

    public Mono<Void> createUser(UserRegistrationDto userDto) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.CREATE, null, userDto);
            sendMessage(event);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    public Mono<Void> deleteUserById(Long id) {

        return Mono.fromRunnable(() -> {
            var event = new Event<>(Event.Type.DELETE, id, null);
            sendMessage(event);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    private void sendMessage(Event<?, ?> event) {
        log.debug("Sending a {} message to {}", event.getEventType(), BINDING_NAME);
        var message = MessageBuilder.withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();
        streamBridge.send(BINDING_NAME, message);
    }

    public Mono<Health> getUserServiceHealth() {
        String uri = userServiceUrl +  "/actuator/health";
        return webClient.get().uri(uri).retrieve().bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()));
    }
}
