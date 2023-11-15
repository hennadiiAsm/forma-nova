package com.formanova.user.api.controller;


import com.formanova.user.api.dto.UserDto;
import com.formanova.user.api.mapper.UserMapper;
import com.formanova.user.persistence.entity.UserEntity;
import com.formanova.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;


@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    Mono<ResponseEntity<UserDto>> getById(@PathVariable Long id) {
        return userService.getById(id)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    Mono<ResponseEntity<Void>> register(@RequestBody @Valid Mono<UserDto> userDto,
                                  UriComponentsBuilder ucb) {
        return userDto
                .doOnError(Throwable::printStackTrace)
                .map(userMapper::toEntity)
                .flatMap(userService::save)
                .map(UserEntity::getId)
                .map(generatedId -> {
                    URI locationOfNewUser = ucb
                            .path("api/v1/users/{id}")
                            .buildAndExpand(generatedId)
                            .toUri();
                    return ResponseEntity.created(locationOfNewUser).build();
                });
    }

}
