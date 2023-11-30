package com.formanova.integration.model;

import com.formanova.common.dto.ReviewDto;
import com.formanova.common.dto.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class UserPublicProfile {

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String phoneNumber;

    private final String country;

    private final String city;

    private final Set<SkillDto> skills;

    private final Set<ReviewDto> reviews;

}
