package com.attractorschool.imurab.mapper.user;

import com.attractorschool.imurab.dto.user.CreateUserDto;
import com.attractorschool.imurab.dto.user.FarmerDto;
import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.User;

public interface UserMapper {
    User convertDtoToEntity(CreateUserDto userDto);

    UserDto convertEntityDto(User user);

    FarmerDto convertToFarmerDto(User user);
}
