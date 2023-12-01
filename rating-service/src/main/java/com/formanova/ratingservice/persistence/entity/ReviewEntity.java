package com.formanova.ratingservice.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("reviews")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(onConstructor_={@PersistenceCreator})
public class ReviewEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    private Instant createdAt;

    private long authorId;

    private String authorFirstName;

    private String authorLastName;

    private long targetId;

    private String skillName; // as well as skill id, since it's the same

    private byte grade;

    private String title;

    private String content;

}
