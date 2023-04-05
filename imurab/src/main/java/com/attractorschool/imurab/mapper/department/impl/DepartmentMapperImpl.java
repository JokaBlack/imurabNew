package com.attractorschool.imurab.mapper.department.impl;

import com.attractorschool.imurab.dto.department.DepartmentCreateDto;
import com.attractorschool.imurab.dto.department.DepartmentDto;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DepartmentMapperImpl implements DepartmentMapper {
    private final AvpMapper avpMapper;
    private final AvpService avpService;

    @Override
    public DepartmentDto convertToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .avpDto(avpMapper.convertEntityToFrontDto(department.getAvp()))
                .avpId(department.getAvp().getId())
                .allowed(department.getAllowed())
                .maxParallelIrrigation(department.getMaxParallelIrrigation())
                .build();
    }

    @Override
    public List<DepartmentDto> convertToDepartmentDto(List<Department> departments) {
        return departments.stream().map(this::convertToDepartmentDto).collect(Collectors.toList());
    }

    @Override
    public Department convertDepartmentCreateDtoToEntity(DepartmentCreateDto createDto) {
        return Department.builder()
                .name(createDto.getName())
                .avp(avpService.findById(createDto.getAvpId()))
                .allowed(false)
                .maxParallelIrrigation(1)
                .build();
    }
}
