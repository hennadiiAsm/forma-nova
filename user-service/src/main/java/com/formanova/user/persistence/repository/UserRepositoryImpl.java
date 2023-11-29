package com.formanova.user.persistence.repository;


import com.formanova.user.persistence.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
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

        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
        CriteriaDelete<UserEntity> criteriaDelete = cb.createCriteriaDelete(UserEntity.class);
        Root<UserEntity> root = criteriaDelete.from(UserEntity.class);

        criteriaDelete.where(cb.equal(root.get("id"), id));

        return sessionFactory
                .withTransaction((session, tx) -> session.createQuery(criteriaDelete)
                        .executeUpdate())
                .convert().with(toMono())
                .then();
    }
}
