package com.formanova.user.persistence.repository;

import com.formanova.user.persistence.entity.SkillEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Mono<SkillEntity> findByName(String name) {
        return Mono.empty();
    }
}
