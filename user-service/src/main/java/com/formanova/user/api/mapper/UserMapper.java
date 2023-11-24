package com.formanova.user.api.mapper;

import com.formanova.common.dto.UserDto;
import com.formanova.user.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMapper {

    private final PaymentCardMapper cardMapper;

    public UserDto toDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getPaymentCards()
                        .parallelStream()
                        .map(cardMapper::toDto)
                        .collect(Collectors.toSet()),
                entity.getPhoneNumber(),
                entity.getCountry(),
                entity.getCity());
    }

    public UserEntity toEntity(UserDto dto) {
        return new UserEntity.Builder(
                dto.getEmail(),
                dto.getPassword(),
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
