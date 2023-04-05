package com.attractorschool.imurab.service.department.impl;

import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.repository.DepartmentRepository;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AvpService avpService;

    @Override
    public Department create(Department department) {
        return saveOrUpdate(department);
    }

    @Override
    public Department saveOrUpdate(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department delete(Department department) {
        department.setDeleted(true);
        return saveOrUpdate(department);
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Boolean existByName(String name) {
        List<Department> departmentList = findAll();
        return departmentList.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    @Override
    public Boolean existByNameOtherDepartments(DepartmentDto departmentDto) {
        List<Department> departmentList = findAll();
        return departmentList.stream()
                .filter(e -> e.getId() != departmentDto.getId())
                .anyMatch(e-> e.getName().equalsIgnoreCase(departmentDto.getName()));

    }

    @Override
    public Department updateDepartment(Department department, DepartmentDto departmentUpdateDto) {
        department.setName(departmentUpdateDto.getName());
        department.setAvp(avpService.findById(departmentUpdateDto.getAvpId()));
        department.setMaxParallelIrrigation(departmentUpdateDto.getMaxParallelIrrigation());
        return saveOrUpdate(department);
    }

    @Override
    public Department updateMaxParallelIrrigation(Department department, Integer maxParallelIrrigation) {
        department.setMaxParallelIrrigation(maxParallelIrrigation);
        return saveOrUpdate(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Department> findAllByAvp(AVP avp) {
        return departmentRepository.findAllByAvp(avp);
    }
}
