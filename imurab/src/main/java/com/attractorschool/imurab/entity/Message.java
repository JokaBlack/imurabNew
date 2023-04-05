package com.attractorschool.imurab.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Where(clause = "deleted = false")
public class Message extends BaseIdEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "message")
    private String message;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "discussion_topic_id")
    @ManyToOne
    private DiscussionTopic topic;

    @Column(name = "deleted")
    @Builder.Default
    private boolean deleted = false;
}
