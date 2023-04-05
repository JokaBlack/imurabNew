package com.attractorschool.imurab.mapper.user;

import com.attractorschool.imurab.dto.user.OperatorDto;
import com.attractorschool.imurab.entity.User;

public interface OperatorMapper {
    OperatorDto convertToOperatorDto(User user);
}
