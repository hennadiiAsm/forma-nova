package com.formanova.common.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(onConstructor_={@JsonCreator})
public class SkillDto {

    @EqualsAndHashCode.Include
    private final String name;

    private final String description;

}
