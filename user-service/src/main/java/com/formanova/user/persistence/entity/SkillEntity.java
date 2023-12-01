package com.formanova.user.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "skills")
public class SkillEntity {

    @Id
    @Length(min = 2, max = 30)
    @EqualsAndHashCode.Include
    private String name;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @Length(min = 2, max = 100)
    private String description;
}
