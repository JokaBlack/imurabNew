package com.attractorschool.imurab.util.validator.avp;

import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.util.logicConfig.LogicConfig;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class AvpValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AvpUpdateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AvpUpdateDto avpUpdateDto = (AvpUpdateDto) target;
        LocalDate currentDate = LocalDate.now();
        LocalDate registrationStartDay = currentDate.plusDays(LogicConfig.registrationStartPeriod);

        if (currentDate.isAfter(avpUpdateDto.getNewDate()) && !avpUpdateDto.isEnd()) {
            errors.rejectValue("date", "avp.season.date.error");
        } else if (avpUpdateDto.getNewDate().isBefore(registrationStartDay) && !avpUpdateDto.isEnd()) {
            errors.rejectValue("date", "avp.season.date.error.period");
        }

        if (currentDate.isAfter(avpUpdateDto.getNewDate()) && avpUpdateDto.isEnd()) {
            errors.rejectValue("date", "avp.season.date.error.end");
        }

    }
}
