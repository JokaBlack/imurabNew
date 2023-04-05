package com.attractorschool.imurab.util.validator.user;

import com.attractorschool.imurab.dto.user.CreateUserDto;
import com.attractorschool.imurab.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CreateUserValidator implements Validator {
    private final UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserDto userDto = (CreateUserDto) target;
        if (service.existsByEmail(userDto.getEmail())) {
            errors.rejectValue("email","user.email.error.exists");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "user.repeatPassword.error.notEquals");
        }
        if (service.existsByPhone(userDto.getPhone())) {
            errors.rejectValue("phone", "user.phone.error.exists");
        }
    }
}
