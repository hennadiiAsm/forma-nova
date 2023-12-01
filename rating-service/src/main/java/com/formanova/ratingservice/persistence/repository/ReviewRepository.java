package com.formanova.ratingservice.persistence.repository;

import com.formanova.ratingservice.persistence.entity.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewRepository extends
        ReactiveCrudRepository<ReviewEntity, Long>,
        ReactiveSortingRepository<ReviewEntity, Long> {

    Flux<ReviewEntity> findAllByAuthorId(Long authorId, Pageable pageable);

    Flux<ReviewEntity> findAllByTargetId(Long targetId, Pageable pageable);

    Mono<Void> deleteAllByTargetId(Long targetId);

}
