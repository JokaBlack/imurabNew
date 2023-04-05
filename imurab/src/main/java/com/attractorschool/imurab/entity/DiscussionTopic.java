package com.attractorschool.imurab.entity;

import com.attractorschool.imurab.util.enums.TopicStatus;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discussion_topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Where(clause = "deleted is false")
public class DiscussionTopic extends BaseIdEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "views")
    @Builder.Default
    private Long views = 0L;

    @Column(name = "messages")
    @Builder.Default
    private Long messages = 0L;

    @OneToMany(mappedBy = "topic")
    private List<Message> userMessages;

    @Column(name = "deleted")
    @Builder.Default
    boolean deleted = false;
}
