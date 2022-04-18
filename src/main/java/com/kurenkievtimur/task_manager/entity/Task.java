package com.kurenkievtimur.task_manager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "tasks")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    TaskType type;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "date")
    LocalDate date;
}
