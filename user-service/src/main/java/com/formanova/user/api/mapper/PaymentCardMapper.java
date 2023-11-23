package com.formanova.user.api.mapper;

import com.formanova.common.dto.PaymentCardDto;
import com.formanova.user.persistence.entity.PaymentCardEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardMapper {

    public PaymentCardDto toDto(PaymentCardEntity entity) {
        return new PaymentCardDto(
                entity.getNumber(),
                entity.getExpirationDate(),
                entity.getSecurityCode()
        );
    }

    public PaymentCardEntity toEntity(PaymentCardDto dto) {
        return new PaymentCardEntity(
                null,
                null,
                dto.getNumber(),
                dto.getExpirationDate(),
                dto.getSecurityCode()
        );
    }

}
