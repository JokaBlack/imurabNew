package com.attractorschool.imurab.dto.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NewsDto {
    private Long id;
    private String title;
    private String image;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy HH:mm")
    private LocalDateTime createdAt;
}
