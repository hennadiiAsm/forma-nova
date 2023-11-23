package com.formanova.user.service;


import com.formanova.user.persistence.entity.UserEntity;
import com.formanova.user.persistence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserEntity> getById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> save(UserEntity user) {
       return userRepository.save(user);
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

}
