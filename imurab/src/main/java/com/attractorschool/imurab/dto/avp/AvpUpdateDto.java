package com.attractorschool.imurab.dto.avp;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvpUpdateDto {

    @NotNull
    private Long id;

    private String name;

    private LocalDate seasonStartAt;

    private LocalDate seasonEndAt;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate newDate;

    @NotNull
    private boolean end;
}
