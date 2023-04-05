package com.attractorschool.imurab.mapper.user.impl;

import com.attractorschool.imurab.dto.user.OperatorDto;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.user.OperatorMapper;
import com.attractorschool.imurab.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperatorMapperImlp implements OperatorMapper {

    @Override
    public OperatorDto convertToOperatorDto(User user) {

        return user == null ? null : OperatorDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .patronymic(user.getPatronymic())
                .role(user.getRole())
                .build();
    }
}
