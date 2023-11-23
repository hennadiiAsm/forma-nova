package com.formanova.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(onConstructor_={@JsonCreator})
public class PaymentCardDto {

    @EqualsAndHashCode.Include
    @CreditCardNumber
    private String number;

    @NotNull
    @Future
    private LocalDate expirationDate;

    @Size(min = 3, max = 4)
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private String securityCode; // also known as CVC, CVV, CAV, etc.

}
