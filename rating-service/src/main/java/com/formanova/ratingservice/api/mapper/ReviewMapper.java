package com.formanova.ratingservice.api.mapper;

import com.formanova.common.dto.ReviewDto;
import com.formanova.ratingservice.persistence.entity.ReviewEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper {

    public ReviewDto toDto(ReviewEntity entity) {
        return new ReviewDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getAuthorId(),
                entity.getAuthorFirstName(),
                entity.getAuthorLastName(),
                entity.getSkillName(),
                entity.getGrade(),
                entity.getTitle(),
                entity.getContent()
        );
    }

    public ReviewEntity toEntity(ReviewDto dto, Long id, long targetId) {
        return new ReviewEntity(
                id,
                null,
                dto.getCreatedAt(),
                dto.getAuthorId(),
                dto.getAuthorFirstName(),
                dto.getAuthorLastName(),
                targetId,
                dto.getSkillName(),
                dto.getGrade(),
                dto.getTitle(),
                dto.getContent()
        );
    }

}
