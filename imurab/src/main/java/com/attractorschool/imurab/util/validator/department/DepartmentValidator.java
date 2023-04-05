package com.attractorschool.imurab.util.validator.department;

import com.attractorschool.imurab.dto.department.DepartmentCreateDto;
import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class DepartmentValidator implements Validator {
    private final DepartmentService departmentService;
    @Override
    public boolean supports(Class<?> clazz) {
        return DepartmentCreateDto.class.equals(clazz) || DepartmentDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof DepartmentCreateDto) {
            DepartmentCreateDto createDto = (DepartmentCreateDto) target;
            if (departmentService.existByName(createDto.getName())) {
                errors.rejectValue("name", "department.name.exist.error");
            }
        } else if(target instanceof DepartmentDto) {
            DepartmentDto updateDto = (DepartmentDto) target;
            if (departmentService.existByNameOtherDepartments(updateDto)) {
                errors.rejectValue("name", "department.name.exist.error");
            }
        }
    }
}
