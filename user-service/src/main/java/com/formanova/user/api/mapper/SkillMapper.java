package com.formanova.user.api.mapper;

import com.formanova.common.dto.SkillDto;
import com.formanova.user.persistence.entity.SkillEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SkillMapper {

    public SkillDto toDto(SkillEntity entity) {
        return new SkillDto(entity.getName(), entity.getDescription());
    }
    
    public SkillEntity toEntity(SkillDto dto) {
        return new SkillEntity(dto.getName(), null, dto.getDescription());
    }
}
