package com.formanova.common.dto.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.formanova.common.dto.SkillDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor(onConstructor_={@JsonCreator})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserPublicDto {

    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") // RFC 5322
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phoneNumber;

    private String country;

    private String city;

    private Set<SkillDto> skills;

    public UserPublicDto(String email,
                         String firstName,
                         String lastName) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
