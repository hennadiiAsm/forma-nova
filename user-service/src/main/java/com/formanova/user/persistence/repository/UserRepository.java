package com.formanova.user.persistence.repository;


import com.formanova.user.persistence.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findById(Long id);

    Mono<UserEntity> save(UserEntity userEntity);

    Mono<Void> deleteById(Long id);

}
