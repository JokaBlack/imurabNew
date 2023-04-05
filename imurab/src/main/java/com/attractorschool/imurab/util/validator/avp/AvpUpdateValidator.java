package com.attractorschool.imurab.util.validator.avp;

import com.attractorschool.imurab.dto.avp.AvpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AvpUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AvpDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AvpDto avpDto = (AvpDto) target;
        LocalDate seasonStartAt = avpDto.getSeasonStartAt();
        LocalDate seasonEndAt = avpDto.getSeasonEndAt();
        LocalDate dateNow = LocalDate.now();
        if(seasonStartAt != null && seasonStartAt.isBefore(dateNow)){
            errors.rejectValue("seasonStartAt","avp.season.date.error");
        }
        if(seasonEndAt != null && seasonEndAt.isBefore(dateNow)){
            errors.rejectValue("seasonEndAt","avp.season.date.error.end");
        }
        if(seasonStartAt != null && seasonEndAt != null && seasonStartAt.isAfter(seasonEndAt)){
            errors.rejectValue("seasonStartAt", "avp.season.date.start.after.end.error");
        }
    }
}
