package com.formanova.user.persistence.repository;


import com.formanova.user.persistence.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserRepositoryImpl implements UserRepository {

    private final Mutiny.SessionFactory sessionFactory;

    @Override
    public Mono<UserEntity> findByEmail(String email) {
        Objects.requireNonNull(email);
        return sessionFactory
                .withSession(session -> session.find(UserEntity.class, email))
                .convert().with(toMono());
    }

    @Override
    public Mono<UserEntity> findById(Long id) {
        Objects.requireNonNull(id);
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

}
