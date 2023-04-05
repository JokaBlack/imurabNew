package com.attractorschool.imurab.dto.queue;

import com.attractorschool.imurab.dto.field.FieldDto;
import com.attractorschool.imurab.dto.user.OperatorDto;
import com.attractorschool.imurab.util.enums.QStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QueueDto {
    private Long id;

    private FieldDto field;

    private LocalDateTime createdTime;

    private QStatus status;

    private OperatorDto operator;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy HH:mm")
    private LocalDateTime startWatering;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy HH:mm")
    private LocalDateTime endWatering;
}
