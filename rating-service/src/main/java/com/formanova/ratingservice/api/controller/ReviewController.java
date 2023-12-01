package com.formanova.ratingservice.api.controller;

import com.formanova.common.dto.ReviewDto;
import com.formanova.ratingservice.api.mapper.ReviewMapper;
import com.formanova.ratingservice.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;

    @GetMapping
    Flux<ReviewDto> showAll(@RequestAttribute("targetId") long id,
                            Pageable pageable) {
        return reviewService.getReviewsByTargetId(id, pageable)
                .map(reviewMapper::toDto)
                .doOnNext(dto -> log.debug("Sending " + dto));
    }

}
