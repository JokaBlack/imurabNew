package com.attractorschool.imurab.dto.department;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepartmentCreateDto {

    @Size(min = 2, max = 50,message = "{department.name.create.error}")
    private String name;
    @NotNull(message = "{department.avpId.empty.error}")
    @Positive
    private Long avpId;
}
