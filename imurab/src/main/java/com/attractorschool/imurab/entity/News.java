package com.attractorschool.imurab.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted = false")
public class News extends BaseIdEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted")
    @Builder.Default
    private boolean deleted = false;
}
