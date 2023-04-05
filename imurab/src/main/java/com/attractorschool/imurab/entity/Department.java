package com.attractorschool.imurab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Where(clause = "deleted = false")
public class Department extends BaseIdEntity {
    @Column(name = "name", length = 25)
    private String name;

    @ManyToOne
    @JoinColumn(name = "avp_id")
    private AVP avp;

    @Column(columnDefinition = "boolean default false")
    private Boolean allowed;

    @Column(name = "max_parallel_irrigation", columnDefinition = "integer default 1")
    private Integer maxParallelIrrigation;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;
}
