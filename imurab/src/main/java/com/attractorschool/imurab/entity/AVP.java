package com.attractorschool.imurab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "avp")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Where(clause = "deleted = false")
public class AVP extends BaseIdEntity {
    @Column(name = "name", length = 25)
    private String name;

    @Column(name = "season_start_at")
    private LocalDate seasonStartAt;

    @Column(name = "season_end_at")
    private LocalDate seasonEndAt;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;
}