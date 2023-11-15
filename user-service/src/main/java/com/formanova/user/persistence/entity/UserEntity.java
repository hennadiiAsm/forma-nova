package com.formanova.user.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @Column(nullable = false)
    private boolean enabled;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") // RFC 5322
    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @OneToMany(mappedBy = "holder", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<@Valid PaymentCardEntity> paymentCards;

    private String phoneNumber;

    private String country;

    private String city;

    public static class Builder {

        private final String email;
        private final String password;
        private final String firstName;
        private final String lastName;
        private final LocalDate birthDate;
        private Set<PaymentCardEntity> paymentCards;
        private String phoneNumber;
        private String country;
        private String city;

        public Builder(String email, String password, String firstName, String lastName, LocalDate birthDate) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDate = birthDate;
        }

        public Builder setPaymentCards(Set<PaymentCardEntity> paymentCards) {
            this.paymentCards = paymentCards;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(null, null, true,
                    email, password, firstName, lastName, birthDate, paymentCards, phoneNumber, country, city);
        }
    }

}

