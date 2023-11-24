package com.formanova.common.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor(onConstructor_={@JsonCreator})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserPublicDto {

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") // RFC 5322
    @Setter(AccessLevel.NONE)
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phoneNumber;

    private String country;

    private String city;

    public UserPublicDto(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
