package com.formanova.user.persistence.repository;

import com.formanova.user.persistence.entity.PaymentCardEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PaymentCardRepositoryImpl implements PaymentCardRepository {

    private final Mutiny.SessionFactory sessionFactory;

    @Override
    public Mono<PaymentCardEntity> findByNumber(String number) {

        Objects.requireNonNull(number);
        return sessionFactory
                .withSession(session -> session.find(PaymentCardEntity.class, number))
                .convert().with(toMono());
    }

    @Override
    public Flux<PaymentCardEntity> findByHolderId(Long holderId) {

        Objects.requireNonNull(holderId);

        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<PaymentCardEntity> query = cb.createQuery(PaymentCardEntity.class);
        Root<PaymentCardEntity> root = query.from(PaymentCardEntity.class);

        query.where(cb.equal(root.get("holder").get("id"), holderId));

        return sessionFactory
                .withSession(session -> session.createQuery(query)
//                        .setFirstResult(offset)
//                        .setMaxResults(limit)
                        .getResultList())
                .convert().with(toMono())
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<PaymentCardEntity> save(PaymentCardEntity entity) {
        return sessionFactory
                .withSession(session -> session.persist(entity)
                        .onItem().call(session::flush))
                .convert().with(toMono())
                .then(Mono.just(entity));
    }
}
