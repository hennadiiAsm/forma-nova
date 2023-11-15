package com.formanova.user.persistence.repository;

import com.formanova.user.persistence.entity.PaymentCardEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentCardRepository {

    Mono<PaymentCardEntity> findByNumber(String number);

    Flux<PaymentCardEntity> findByHolderId(Long holderId);

    Mono<PaymentCardEntity> save(PaymentCardEntity entity);

}
