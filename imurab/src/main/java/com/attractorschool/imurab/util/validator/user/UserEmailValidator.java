package com.attractorschool.imurab.util.validator.user;

import com.attractorschool.imurab.dto.user.UserEmailDto;
import com.attractorschool.imurab.entity.PasswordToken;
import com.attractorschool.imurab.service.token.PasswordTokenService;
import com.attractorschool.imurab.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class UserEmailValidator implements Validator {
    private final UserService userService;
    private final PasswordTokenService tokenService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEmailDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEmailDto emailDto = (UserEmailDto) target;
        PasswordToken token = tokenService.findByUserEmail(emailDto.getEmail());
        if (!userService.existsByEmail(emailDto.getEmail())) {
            errors.rejectValue("email", "user.email.error.notExists");
        } else if (nonNull(token) && token.getExpiredAt().isAfter(LocalDateTime.now())) {
            errors.rejectValue("email", "mail.resetPassword.error.resend");
        }
    }
}
