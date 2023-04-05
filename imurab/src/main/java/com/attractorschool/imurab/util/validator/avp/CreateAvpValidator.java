package com.attractorschool.imurab.util.validator.avp;

import com.attractorschool.imurab.dto.avp.AvpCreateDto;
import com.attractorschool.imurab.service.avp.AvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
@RequiredArgsConstructor
public class CreateAvpValidator implements Validator {
    private final AvpService avpService;
    @Override
    public boolean supports(Class<?> clazz) {
        return AvpCreateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AvpCreateDto createDto = (AvpCreateDto) target;
        if(avpService.existByName(createDto.getName())){
            errors.rejectValue("name","avp.name.exist.error");
        }
    }
}
