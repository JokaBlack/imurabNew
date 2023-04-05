package com.attractorschool.imurab.dto.topic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DiscussionTopicDto {
    private Long id;
    private String title;
    private String description;
    private String fio;
    private Long views;
    private Long messages;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy HH:mm")
    private LocalDateTime createdAt;
}
