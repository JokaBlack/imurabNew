package com.kurenkievtimur.task_manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "type")
    TaskType type;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "date")
    LocalDateTime date;
}
