package com.attractorschool.imurab.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_avp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Where(clause = "deleted = false")
public class NewsAvp extends BaseIdEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "avp_id")
    private AVP avp;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted")
    @Builder.Default
    private boolean deleted = false;
}
