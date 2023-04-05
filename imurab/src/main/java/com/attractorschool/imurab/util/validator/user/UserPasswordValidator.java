package com.attractorschool.imurab.util.validator.user;

import com.attractorschool.imurab.dto.user.UserPasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserPasswordDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPasswordDto passwordDto = (UserPasswordDto) target;
        if (!passwordDto.getPassword().equals(passwordDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "user.repeatPassword.error.notEquals");
        }
    }
}
