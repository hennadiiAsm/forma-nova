package com.formanova.user.persistence.repository;

import com.formanova.user.persistence.entity.SkillEntity;
import reactor.core.publisher.Mono;

public interface SkillRepository {

    Mono<SkillEntity> findByName(String name);

}
