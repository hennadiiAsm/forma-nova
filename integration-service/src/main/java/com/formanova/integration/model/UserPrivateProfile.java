package com.formanova.integration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.formanova.common.dto.PaymentCardDto;
import com.formanova.common.dto.ReviewDto;
import com.formanova.common.dto.SkillDto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class UserPrivateProfile extends UserPublicProfile {

    private final LocalDate birthDate;

    private final Set<PaymentCardDto> paymentCards;


    public UserPrivateProfile(UserPublicProfile upp,
                              LocalDate birthDate,
                              Set<PaymentCardDto> paymentCards) {

        super(upp.getEmail(), upp.getFirstName(), upp.getLastName(), upp.getPhoneNumber(),
                upp.getCountry(), upp.getCity(), upp.getSkills(), upp.getReviews());

        this.birthDate = birthDate;
        this.paymentCards = paymentCards;
    }

    @JsonCreator
    public UserPrivateProfile(String email,
                              String firstName,
                              String lastName,
                              String phoneNumber,
                              String country,
                              String city,
                              Set<SkillDto> skills,
                              Set<ReviewDto> reviews,
                              LocalDate birthDate,
                              Set<PaymentCardDto> paymentCards) {

        super(email, firstName, lastName, phoneNumber, country, city, skills, reviews);
        this.birthDate = birthDate;
        this.paymentCards = paymentCards;
    }

}
