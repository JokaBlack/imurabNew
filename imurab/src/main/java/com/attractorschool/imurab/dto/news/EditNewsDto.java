package com.attractorschool.imurab.dto.news;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditNewsDto {
    private Long id;

    @NotEmpty(message = "{news.title.error.notEmpty}")
    private String title;

    private MultipartFile image;

    @NotEmpty(message = "{news.image.text.notEmpty}")
    private String text;
}
