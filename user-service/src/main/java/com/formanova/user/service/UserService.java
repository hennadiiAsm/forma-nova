package com.formanova.user.service;


import com.formanova.user.persistence.entity.UserEntity;
import com.formanova.user.persistence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id)
                .filter(UserEntity::isEnabled);
    }

    public Mono<UserEntity> saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Mono<Void> deleteUserById(Long id) {
        return getUserById(id)
                .doOnNext(entity -> entity.setEnabled(false))
                .doOnNext(userRepository::save)
                .then();
    }

}
