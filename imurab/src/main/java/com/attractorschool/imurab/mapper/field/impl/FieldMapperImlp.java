package com.attractorschool.imurab.mapper.field.impl;

import com.attractorschool.imurab.dto.field.CreateFieldDto;
import com.attractorschool.imurab.dto.field.EditFieldDto;
import com.attractorschool.imurab.dto.field.FieldDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsDto;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.department.DepartmentMapper;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.user.UserMapper;
import com.attractorschool.imurab.service.department.DepartmentService;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import com.attractorschool.imurab.util.enums.FieldStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FieldMapperImlp implements FieldMapper {
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;
    private final FieldCropsService fieldCropsService;
    private final DepartmentService departmentService;

    @Override
    public FieldDto convertToFieldDto(Field field) {
        return FieldDto.builder()
                .id(field.getId())
                .name(field.getName())
                .size(field.getSize())
                .fieldCropsDto(FieldCropsDto.builder()
                        .id(field.getFieldCrops().getId())
                        .name(field.getFieldCrops().getName())
                        .coefficient(field.getFieldCrops().getCoefficient())
                        .imgLink(field.getFieldCrops().getImgLink())
                        .build())
                .farmer(userMapper.convertToFarmerDto(field.getUser()))
                .department(departmentMapper.convertToDepartmentDto(field.getDepartment()))
                .status(field.getStatus().name())
                .extraTime(field.getExtraTime() == null ? 0: field.getExtraTime())
                .build();
    }

    @Override
    public EditFieldDto convertToEditFieldDto(Field field) {
        return EditFieldDto.builder()
                .id(field.getId())
                .name(field.getName())
                .size(field.getSize())
                .fieldCropsId(field.getFieldCrops().getId())
                .departmentId(field.getDepartment().getId())
                .build();
    }

    @Override
    public Field convertFieldDtoToEntity(CreateFieldDto fieldDto, User user) {
        return Field.builder()
                .name(fieldDto.getName())
                .size(fieldDto.getSize())
                .fieldCrops(fieldCropsService.findById(fieldDto.getFieldCropsId()))
                .department(departmentService.findById(fieldDto.getDepartmentId()))
                .user(user)
                .status(FieldStatus.CREATED)
                .build();
    }
}
