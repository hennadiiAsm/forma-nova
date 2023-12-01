package com.formanova.user.persistence.repository;

import com.formanova.user.persistence.entity.SkillEntity;
import io.smallrye.mutiny.Uni;
import reactor.core.publisher.Mono;

public interface SkillRepository {

    Mono<SkillEntity> findByName(String name);

    Uni<SkillEntity> save(SkillEntity entity);

}
