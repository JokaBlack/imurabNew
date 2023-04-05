package com.attractorschool.imurab.dto.message;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditMessageDto {
    private Long id;
    @NotEmpty(message = "{message.notEmpty}")
    private String message;
}
