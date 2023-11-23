package com.formanova.integration.api.v1.controller;


import com.formanova.common.dto.UserDto;
import com.formanova.integration.service.IntegrationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserController {

    private final IntegrationService integration;

    @GetMapping("/{id}")
    Mono<ResponseEntity<UserDto>> getById(@PathVariable Long id) {
        return integration.getUserById(id);
    }

    @PostMapping("/register")
    Mono<ResponseEntity<Void>> register(@RequestBody @Valid Mono<UserDto> userDto) {
        return userDto
                .flatMap(integration::saveUser)
                .then(Mono.fromCallable(() -> ResponseEntity.accepted().build()));
    }

}
