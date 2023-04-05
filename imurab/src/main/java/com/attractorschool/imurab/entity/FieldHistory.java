package com.attractorschool.imurab.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fields_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FieldHistory extends BaseIdEntity {
    @Column(name = "startedAt")
    private LocalDateTime startedAt;

    @Column(name = "endedAt")
    private LocalDateTime endedAt;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
