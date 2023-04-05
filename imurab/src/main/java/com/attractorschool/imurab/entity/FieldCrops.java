package com.attractorschool.imurab.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "field_crops")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Where(clause = "deleted = false")
public class FieldCrops extends BaseIdEntity {
    @Column(name = "name", length = 45)
    private String name;

    @Column(columnDefinition = "numeric default 1")
    private BigDecimal coefficient;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;
}
