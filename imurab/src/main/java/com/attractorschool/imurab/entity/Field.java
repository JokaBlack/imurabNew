package com.attractorschool.imurab.entity;

import com.attractorschool.imurab.util.enums.FieldStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "fields")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Field extends BaseIdEntity {
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "size")
    private BigDecimal size;

    @ManyToOne
    @JoinColumn(name = "field_crops_id")
    private FieldCrops fieldCrops;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "extra_time", columnDefinition = "int default 0")
    @Builder.Default
    private Integer extraTime = 0;

    @Column(name = "archived", columnDefinition = "boolean default false")
    private boolean archived;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FieldStatus status;

    @OneToMany(mappedBy = "field")
    List<FieldHistory> histories;
}