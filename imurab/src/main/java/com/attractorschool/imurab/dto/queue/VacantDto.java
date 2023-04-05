package com.attractorschool.imurab.dto.queue;

import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.dto.field.FieldDto;
import com.attractorschool.imurab.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VacantDto {
    private FieldDto field;
    private Vacant vacant;
}
