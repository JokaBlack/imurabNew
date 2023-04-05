package com.attractorschool.imurab.service.field;

import com.attractorschool.imurab.dto.field.EditFieldDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.Department;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.service.BaseService;
import com.attractorschool.imurab.util.enums.FieldStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FieldService extends BaseService<Field> {
    Page<Field> findAllByUser(Pageable pageable, User user);

    Field update(Field field, EditFieldDto editFieldDto);

    Field confirm(Field field);

    double getIrrigationTime(Field field);

    Page<Field> findAllByStatus(Pageable pageable, FieldStatus status);

    Field reject(Field field);

    Page<Field> findAllByAvp(Pageable pageable, AVP avp, FieldStatus status);

    Page<Field> findAllByDepartment(Pageable pageable, Department department, FieldStatus status);
}
