package com.attractorschool.imurab.dto.news.avp;

import com.attractorschool.imurab.dto.avp.AvpDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NewsAvpDto {
    private Long id;

    private String title;

    private String text;

    private String image;

    private AvpDto avpDto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy HH:mm")
    private LocalDateTime createdAt;
}
