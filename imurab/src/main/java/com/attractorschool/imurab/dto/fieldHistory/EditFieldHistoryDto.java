package com.attractorschool.imurab.dto.fieldHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditFieldHistoryDto {
    private Long id;

    @NotNull(message = "{field.history.startedAt.error.notNull}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startedAt;

    @NotNull(message = "{field.history.endedAt.error.notNull}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endedAt;

    private Long fieldId;

    @NotEmpty(message = "{field.history.description.error.notEmpty}")
    private String description;
}
