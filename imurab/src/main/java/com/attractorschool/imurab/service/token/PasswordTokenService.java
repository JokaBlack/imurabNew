package com.attractorschool.imurab.service.token;

import com.attractorschool.imurab.entity.PasswordToken;
import com.attractorschool.imurab.service.BaseService;

import java.util.List;

public interface PasswordTokenService extends BaseService<PasswordToken> {
    @Override
    PasswordToken create(PasswordToken passwordToken);

    @Override
    PasswordToken saveOrUpdate(PasswordToken passwordToken);

    @Override
    PasswordToken delete(PasswordToken passwordToken);

    @Override
    PasswordToken findById(Long id);

    PasswordToken findByToken(String token);

    PasswordToken findByUserEmail(String email);

    PasswordToken refreshToken(PasswordToken token);
}
