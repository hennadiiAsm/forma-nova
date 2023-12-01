package com.formanova.ratingservice.service;

import com.formanova.ratingservice.persistence.entity.ReviewEntity;
import com.formanova.ratingservice.persistence.repository.ReviewRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Mono<ReviewEntity> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Flux<ReviewEntity> getReviewsByAuthorId(Long id, Pageable pageable) {
        return reviewRepository.findAllByAuthorId(id, pageable);
    }

    public Flux<ReviewEntity> getReviewsByTargetId(Long id, Pageable pageable) {
        return reviewRepository.findAllByTargetId(id, pageable);
    }

}
