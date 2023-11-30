package com.formanova.ratingservice.api.mapper;

import com.formanova.common.dto.ReviewDto;
import com.formanova.ratingservice.persistence.entity.ReviewEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper {

    public ReviewDto toDto(ReviewEntity entity) {
        return new ReviewDto(
                entity.getCreatedAt(),
                entity.getAuthorId(),
                entity.getAuthorFirstName(),
                entity.getAuthorLastName(),
                entity.getSkillId(),
                entity.getGrade(),
                entity.getTitle(),
                entity.getContent());
    }

}
