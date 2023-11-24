package com.formanova.integration.api.v1.controller;


import com.formanova.common.dto.UserDto;
import com.formanova.integration.client.IntegrationClient;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserController {

    private final IntegrationClient integration;

    @GetMapping
    Mono<ResponseEntity<List<UserDto>>> getUsers(
            @RequestParam(name = "k", required = false) String keyword,
            @RequestParam(name = "s", required = false) String sortOrder) {

    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<UserDto>> getById(@PathVariable Long id) {
        return integration.getUserById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> register(@RequestBody @Valid Mono<UserDto> userDto) {
        return userDto
                .flatMap(integration::saveUser)
                .then(Mono.fromCallable(() -> ResponseEntity.accepted().build()));
    }

}
