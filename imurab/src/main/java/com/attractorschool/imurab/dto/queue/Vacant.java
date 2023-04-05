package com.attractorschool.imurab.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Vacant {
    @NotNull(message = "{queue.vacant.select.error}")
    long departmentId;
    @AssertTrue(message = "{queue.vacant.select.error}")
    boolean ok;
    @Future(message = "{queue.vacant.date.error}")
    LocalDate startDate;
    @Min(value = 0,message = "{queue.vacant.min.error}")
    @Max(value = 24,message = "{queue.vacant.max.error}")
    int startTime;
    @Min(value = 1, message = "{queue.vacant.hour.error}")
    int hour;
}
