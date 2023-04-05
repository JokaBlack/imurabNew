package com.attractorschool.imurab.mapper.department;

import com.attractorschool.imurab.dto.department.DepartmentCreateDto;
import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.Department;

import java.util.List;

public interface DepartmentMapper {
    DepartmentDto convertToDepartmentDto(Department department);

    List<DepartmentDto> convertToDepartmentDto(List<Department> departments);
    Department convertDepartmentCreateDtoToEntity(DepartmentCreateDto createDto);
}
