package com.formanova.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(onConstructor_={@JsonCreator})
public class ReviewDto {

    @EqualsAndHashCode.Include
    private Instant createdAt;

    @EqualsAndHashCode.Include
    private long authorId;

    private String authorFirstName;

    private String authorLastName;

    private long skillId;

    private int grade;

    private String title;

    private String content;

}