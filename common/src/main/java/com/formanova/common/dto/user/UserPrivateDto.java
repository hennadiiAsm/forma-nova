package com.formanova.common.dto.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.formanova.common.dto.PaymentCardDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

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
                          Set<PaymentCardDto> paymentCards) {

        super(email, firstName, lastName, phoneNumber, country, city);
        this.birthDate = birthDate;
        this.paymentCards = paymentCards;
    }

}

