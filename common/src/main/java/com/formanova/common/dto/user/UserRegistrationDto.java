package com.formanova.common.dto.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.formanova.common.dto.PaymentCardDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
public class UserRegistrationDto extends UserPrivateDto {

    @NotBlank
    private String password;

    public UserRegistrationDto(String email,
                               String firstName,
                               String lastName,
                               LocalDate birthDate,
                               Set<PaymentCardDto> paymentCards,
                               String password) {

        super(email, firstName, lastName, birthDate, paymentCards);
        this.password = password;
    }

    @JsonCreator
    public UserRegistrationDto(String email,
                               String firstName,
                               String lastName,
                               String phoneNumber,
                               String country,
                               String city,
                               LocalDate birthDate,
                               Set<PaymentCardDto> paymentCards,
                               String password) {

        super(email, firstName, lastName, phoneNumber, country, city, birthDate, paymentCards);
        this.password = password;
    }

}
