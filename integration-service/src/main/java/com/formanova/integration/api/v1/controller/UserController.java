package com.formanova.integration.api.v1.controller;


import com.formanova.integration.model.UserPublicProfile;
import com.formanova.integration.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/{id}")
    Mono<? extends ResponseEntity<? extends UserPublicProfile>> showById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id)
                .map(ResponseEntity::ok);
    }

}
