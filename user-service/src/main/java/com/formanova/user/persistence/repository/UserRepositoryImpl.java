package com.formanova.user.persistence.repository;


import com.formanova.user.persistence.entity.UserEntity;
import com.formanova.user.persistence.entity.UserEntity_;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRepositoryImpl implements UserRepository {

    private final Mutiny.SessionFactory sessionFactory;

    @Override
    public Mono<UserEntity> findByEmail(String email) {
        return sessionFactory
                .withSession(session -> session.find(UserEntity.class, email))
                .convert().with(toMono());
    }

    @Override
    public Mono<UserEntity> findById(Long id) {
        return sessionFactory
                .withSession(session -> session.find(UserEntity.class, id))
                .convert().with(toMono());
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            return sessionFactory
                    .withSession(session -> session.persist(userEntity)
                            .chain(session::flush))
                    .convert().with(toMono())
                    .then(Mono.just(userEntity));
        } else {
            return sessionFactory
                    .withSession(session -> session.merge(userEntity)
                            .onItem().call(session::flush))
                    .convert().with(toMono());
        }
    }

    @Override
    public Mono<Void> deleteById(Long id) {

        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaUpdate<UserEntity> update = cb.createCriteriaUpdate(UserEntity.class);
        Root<UserEntity> root = update.from(UserEntity.class);

        update.set(root.get(UserEntity_.enabled), false).where(cb.equal(root.get(UserEntity_.id), id));

        return sessionFactory
                .withTransaction((session, tx) -> session.createQuery(update)
                        .executeUpdate())
                .convert().with(toMono())
                .then();
    }
}
