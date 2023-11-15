package com.formanova.user.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment_cards")
@Entity
public class PaymentCardEntity {

    @Version
    private Long version;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "holder_id")
    private UserEntity holder;

    @Id
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
