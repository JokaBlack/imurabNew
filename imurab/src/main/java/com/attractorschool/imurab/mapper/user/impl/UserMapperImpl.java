package com.attractorschool.imurab.mapper.user.impl;

import com.attractorschool.imurab.dto.user.CreateUserDto;
import com.attractorschool.imurab.dto.user.FarmerDto;
import com.attractorschool.imurab.dto.user.UserDto;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.user.UserMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.util.enums.Role;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.attractorschool.imurab.util.enums.Role.ROLE_FARMER;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder encoder;
    private final AvpService avpService;

    @Override
    public User convertDtoToEntity(CreateUserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .patronymic(userDto.getPatronymic())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .avp(avpService.findById(userDto.getAvpId()))
                .password(encoder.encode(userDto.getPassword()))
                .role(isNull(userDto.getRole()) ? ROLE_FARMER : userDto.getRole())
                .image(nonNull(userDto.getImage()) && isNotEmpty(userDto.getImage().getOriginalFilename()) ?
                        userDto.getImage().getOriginalFilename() : "default_image.jpg")
                .build();
    }

    @Override
    public UserDto convertEntityDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .avpId(user.getAvp().getId())
                .locked(user.isLocked())
                .build();
    }

    @Override
    public FarmerDto convertToFarmerDto(User user) {
        return FarmerDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .patronymic(user.getPatronymic())
                .build();
    }
}
