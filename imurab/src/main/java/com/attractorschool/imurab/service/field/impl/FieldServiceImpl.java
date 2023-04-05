package com.attractorschool.imurab.service.field.impl;

import com.attractorschool.imurab.dto.field.EditFieldDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.repository.FieldRepository;
import com.attractorschool.imurab.service.department.DepartmentService;
import com.attractorschool.imurab.service.field.FieldService;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import com.attractorschool.imurab.util.enums.FieldStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository repository;
    private final FieldCropsService cropsService;
    private final DepartmentService departmentService;

    @Override
    public Field create(Field field) {
        return saveOrUpdate(field);
    }

    @Override
    public Field saveOrUpdate(Field field) {
        return repository.save(field);
    }

    @Override
    public Field delete(Field field) {
        field.setArchived(true);
        return saveOrUpdate(field);
    }

    @Override
    public Field findById(Long id) {
        return repository.findByIdAndArchivedIsFalse(id)
                .orElse(null);
    }

    @Override
    public Page<Field> findAllByUser(Pageable pageable, User user) {
        return repository.findAllByUserAndArchivedIsFalse(pageable, user);
    }

    @Override
    public Field update(Field field, EditFieldDto editFieldDto) {
        field.setName(editFieldDto.getName());
        field.setSize(editFieldDto.getSize());
        field.setFieldCrops(cropsService.findById(editFieldDto.getFieldCropsId()));
        field.setDepartment(departmentService.findById(editFieldDto.getDepartmentId()));
        return saveOrUpdate(field);
    }

    @Override
    public Field confirm(Field field) {
        field.setStatus(FieldStatus.CONFIRMED);
        return saveOrUpdate(field);
    }

    public double getIrrigationTime(Field field) {
        return (field.getSize().doubleValue() * 8) + field.getExtraTime();
    }

    @Override
    public Page<Field> findAllByStatus(Pageable pageable, FieldStatus status) {
        return repository.findAllByStatus(pageable, status);
    }

    @Override
    public Field reject(Field field) {
        repository.delete(field);
        return field;
    }

    @Override
    public Page<Field> findAllByAvp(Pageable pageable, AVP avp, FieldStatus status) {
        return repository.findAllByDepartmentAvpAndStatus(pageable, avp, status);
    }

    @Override
    public Page<Field> findAllByDepartment(Pageable pageable, Department department, FieldStatus status) {
        return repository.findAllByDepartmentAndStatus(pageable, department, status);
    }
}
