package com.formanova.integration.api.v1.controller;


import com.formanova.common.dto.user.UserPublicDto;
import com.formanova.common.dto.user.UserRegistrationDto;
import com.formanova.integration.client.UserServiceClient;
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

    private final UserServiceClient integration;

//    @GetMapping
//    Mono<ResponseEntity<List<UserPrivateDto>>> getUsers(
//            @RequestParam(name = "k", required = false) String keyword,
//            @RequestParam(name = "s", required = false) String sortOrder) {
//
//    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<UserPublicDto>> getById(@PathVariable Long id) {
        return integration.getUserById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> register(@RequestBody @Valid Mono<UserRegistrationDto> userDto) {
        return userDto
                .flatMap(integration::createUser)
                .then(Mono.fromCallable(() -> ResponseEntity.accepted().build()));
    }

}
