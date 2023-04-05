package com.attractorschool.imurab.mapper.token;

import com.attractorschool.imurab.dto.token.PasswordTokenDto;
import com.attractorschool.imurab.dto.user.UserEmailDto;
import com.attractorschool.imurab.entity.PasswordToken;

public interface PasswordTokenMapper {
    PasswordToken convertDtoToEntity(UserEmailDto emailDto);

    PasswordTokenDto convertEntityToDto(PasswordToken token);
}
