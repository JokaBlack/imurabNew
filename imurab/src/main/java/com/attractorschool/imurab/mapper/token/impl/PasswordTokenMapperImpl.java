package com.attractorschool.imurab.mapper.token.impl;

import com.attractorschool.imurab.dto.token.PasswordTokenDto;
import com.attractorschool.imurab.dto.user.UserEmailDto;
import com.attractorschool.imurab.entity.PasswordToken;
import com.attractorschool.imurab.mapper.token.PasswordTokenMapper;
import com.attractorschool.imurab.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PasswordTokenMapperImpl implements PasswordTokenMapper {
    private final UserService userService;

    @Override
    public PasswordToken convertDtoToEntity(UserEmailDto emailDto) {
        PasswordToken token = new PasswordToken();
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(token.getCreatedAt().plusHours(24));
        token.setUser(userService.findByEmail(emailDto.getEmail()));
        token.setToken(UUID.randomUUID().toString());
        return token;
    }

    @Override
    public PasswordTokenDto convertEntityToDto(PasswordToken token) {
        return PasswordTokenDto.builder()
                .id(token.getId())
                .createdAt(token.getCreatedAt())
                .expiredAt(token.getExpiredAt())
                .confirmedAt(token.getConfirmedAt())
                .token(token.getToken())
                .build();
    }
}
