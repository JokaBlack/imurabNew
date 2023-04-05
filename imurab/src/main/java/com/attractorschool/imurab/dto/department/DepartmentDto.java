package com.attractorschool.imurab.dto.department;

import com.attractorschool.imurab.dto.avp.AvpDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentDto {
    private Long id;

    @Size(min = 2, max = 50,message = "{avp.create.error}")
    private String name;

    private AvpDto avpDto;

    @NotNull(message = "{department.avpId.empty.error}")
    @Positive
    private Long avpId;

    private Boolean allowed;

    @NotNull(message = "{department.maxParallelIrrigation.empty.error}")
    @Positive(message = "{department.maxParallelIrrigation.empty.error}")
    private Integer maxParallelIrrigation;
}
