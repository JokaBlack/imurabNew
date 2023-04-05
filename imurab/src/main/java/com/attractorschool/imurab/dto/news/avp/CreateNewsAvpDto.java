package com.attractorschool.imurab.dto.news.avp;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateNewsAvpDto {
    @NotEmpty(message = "{news.title.error.notEmpty}")
    private String title;

    private MultipartFile image;

    @NotEmpty(message = "{news.image.text.notEmpty}")
    private String text;

    @NotNull(message = "{news.avp.error.notNull}")
    private Long avpId;
}
