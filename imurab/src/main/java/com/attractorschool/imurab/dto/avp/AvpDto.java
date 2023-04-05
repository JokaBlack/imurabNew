package com.attractorschool.imurab.dto.avp;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvpDto {
    private Long id;

    @Size(min = 2, max = 50,message = "{avp.create.error}")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate seasonStartAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate seasonEndAt;
}
