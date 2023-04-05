package com.attractorschool.imurab.util.validator;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.service.fieldCrops.FieldCropsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FieldCropsValidator implements Validator {
    private final FieldCropsService fieldCropsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FieldCropsCreateDto.class.equals(clazz) || FieldCropsUpdateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof FieldCropsCreateDto) {
            FieldCropsCreateDto createDto = (FieldCropsCreateDto) target;
            if (fieldCropsService.existByName(createDto.getName())) {
                errors.rejectValue("name", "fieldCrops.name.exist.error");
            }
            if(createDto.getMultipartFile().getSize() == 0){
                errors.rejectValue("multipartFile", "fieldCrops.multipartFile.error");
            }
        } else if (target instanceof FieldCropsUpdateDto) {
            FieldCropsUpdateDto updateDto = (FieldCropsUpdateDto) target;
            if(fieldCropsService.existByNameOtherFieldCrops(updateDto)){
               errors.rejectValue("name", "fieldCrops.name.exist.error");
            }
        }
    }
}
