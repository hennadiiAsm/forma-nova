package com.formanova.common.dto.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.formanova.common.dto.PaymentCardDto;
import com.formanova.common.dto.SkillDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;


@ToString(callSuper = true)
@Getter @Setter
public class UserPrivateDto extends UserPublicDto {

    @NotNull
    private LocalDate birthDate;

    private Set<@Valid PaymentCardDto> paymentCards;

    public UserPrivateDto(String email,
                          String firstName,
                          String lastName,
                          LocalDate birthDate,
                          Set<PaymentCardDto> paymentCards) {

        super(email, firstName, lastName);
        this.birthDate = birthDate;
        this.paymentCards = paymentCards;
    }

    @JsonCreator
    public UserPrivateDto(String email,
                          String firstName,
                          String lastName,
                          String phoneNumber,
                          String country,
                          String city,
                          LocalDate birthDate,
                          Set<PaymentCardDto> paymentCards,
                          Set<SkillDto> skills) {

        super(email, firstName, lastName, phoneNumber, country, city, skills);
        this.birthDate = birthDate;
        this.paymentCards = paymentCards;
    }

//    @SuppressWarnings("all")
    public LocalDate getBirthDate() {
        return birthDate;
    }
}

