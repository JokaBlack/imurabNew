package com.attractorschool.imurab.mapper.field;

import com.attractorschool.imurab.dto.field.CreateFieldDto;
import com.attractorschool.imurab.dto.field.EditFieldDto;
import com.attractorschool.imurab.dto.field.FieldDto;
import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.User;

public interface FieldMapper {
    FieldDto convertToFieldDto(Field field);

    EditFieldDto convertToEditFieldDto(Field field);

    Field convertFieldDtoToEntity(CreateFieldDto fieldDto, User user);
}
