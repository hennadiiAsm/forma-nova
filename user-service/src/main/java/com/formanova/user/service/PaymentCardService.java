package com.formanova.user.service;

import com.formanova.user.persistence.entity.PaymentCardEntity;
import com.formanova.user.persistence.repository.PaymentCardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;

    public Flux<PaymentCardEntity> getByHolderId(long holderId) {
        return paymentCardRepository.findByHolderId(holderId);
    }


}
