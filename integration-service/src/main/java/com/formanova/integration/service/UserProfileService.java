package com.formanova.integration.service;

import com.formanova.common.dto.ReviewDto;
import com.formanova.common.dto.user.UserPrivateDto;
import com.formanova.integration.client.ReviewServiceClient;
import com.formanova.integration.client.UserServiceClient;
import com.formanova.integration.model.UserPrivateProfile;
import com.formanova.integration.model.UserPublicProfile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserProfileService {

    private final UserServiceClient userServiceClient;

    private final ReviewServiceClient reviewServiceClient;

    public Mono<? extends UserPublicProfile> getUserProfileById(Long id) {

        return Mono.zip(
                        values -> createUserProfile((UserPrivateDto) values[0], (Set<ReviewDto>) values[1]),
                        userServiceClient.getUserById(id),
                        reviewServiceClient.getReviewsByTargetId(id).collectList()
                )
                .doOnError(ex -> log.warn("getUserProfileById failed: {}", ex.toString()));
    }

    private UserPublicProfile createUserProfile(UserPrivateDto userDto, Set<ReviewDto> reviews) {
        UserPublicProfile publicProfile = new UserPublicProfile(
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhoneNumber(),
                userDto.getCountry(),
                userDto.getCity(),
                userDto.getSkills(),
                reviews);
        return userDto.getBirthDate() == null
                ?
                publicProfile
                :
                new UserPrivateProfile(
                        publicProfile,
                        userDto.getBirthDate(),
                        userDto.getPaymentCards());
    }


}
