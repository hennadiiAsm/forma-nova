package com.formanova.integration.api.v1.controller;


import com.formanova.common.dto.user.UserRegistrationDto;
import com.formanova.integration.model.UserPublicProfile;
import com.formanova.integration.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}")
    Mono<ResponseEntity<? extends UserPublicProfile>> showById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> register(Mono<UserRegistrationDto> registrationDto) {
        return registrationDto
                .flatMap(userProfileService::addUserProfile)
                .thenReturn(ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return userProfileService.deleteUserProfileById(id)
                .thenReturn(ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

}
