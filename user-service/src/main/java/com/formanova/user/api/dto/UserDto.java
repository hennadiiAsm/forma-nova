package com.formanova.user.api.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor(onConstructor_={@JsonCreator})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {

    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") // RFC 5322
    @Setter(AccessLevel.NONE)
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    private Set<@Valid PaymentCardDto> paymentCards;

    private String phoneNumber;

    private String country;

    private String city;

    public UserDto(String email, String password, String firstName, String lastName, @NotNull LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

}
