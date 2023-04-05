package com.attractorschool.imurab.service.department;

import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService extends BaseService<Department> {

    List<Department> getAllDepartments();

    Page<Department> findAll(Pageable pageable);
    Boolean existByName(String name);
    Boolean existByNameOtherDepartments(DepartmentDto departmentDto);
    Department updateDepartment(Department department, DepartmentDto departmentUpdateDto);
    Department updateMaxParallelIrrigation(Department department, Integer maxParallelIrrigation);

    List<Department> findAll();

    List<Department> findAllByAvp(AVP avp);

}
