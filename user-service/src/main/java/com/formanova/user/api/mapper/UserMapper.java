package com.formanova.user.api.mapper;

import com.formanova.common.dto.user.UserPrivateDto;
import com.formanova.common.dto.user.UserPublicDto;
import com.formanova.common.dto.user.UserRegistrationDto;
import com.formanova.user.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMapper {

    private final PaymentCardMapper cardMapper;

    public UserPublicDto toPublicDto(UserEntity entity) {
        return new UserPublicDto(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getCountry(),
                entity.getCity());
    }

    public UserPrivateDto toPrivateDto(UserEntity entity) {
        return new UserPrivateDto(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getCountry(),
                entity.getCity(),
                entity.getBirthDate(),
                entity.getPaymentCards()
                        .parallelStream()
                        .map(cardMapper::toDto)
                        .collect(Collectors.toSet())
                );
    }

    public UserEntity toEntity(UserRegistrationDto dto) {
        return toEntity(
                null,
                new UserPrivateDto(
                        dto.getEmail(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPhoneNumber(),
                        dto.getCountry(),
                        dto.getCity(),
                        dto.getBirthDate(),
                        dto.getPaymentCards()
                ),
                dto.getPassword()
        );
    }

    public UserEntity toEntity(Long id,
                               UserPrivateDto dto,
                               String password) {

        return new UserEntity.Builder(
                id,
                dto.getEmail(),
                password,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate())
                .setPaymentCards(
                        dto.getPaymentCards()
                                .parallelStream()
                                .map(cardMapper::toEntity)
                                .collect(Collectors.toSet())
                )
                .setPhoneNumber(dto.getPhoneNumber())
                .setCountry(dto.getCountry())
                .setCity(dto.getCity())
                .build();
    }

}
