package com.attractorschool.imurab.mapper.fieldCrops;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.entity.FieldCrops;

import java.util.List;

public interface FieldCropsMapper {
    FieldCropsDto convertEntityDto(FieldCrops fieldCrops);
    FieldCropsUpdateDto convertEntityToFieldCropsUpdateDto(FieldCrops fieldCrops);

    List<FieldCropsDto> convertEntityToDto(List<FieldCrops> fieldCrops);

    FieldCrops convertFieldCropsCreateDtoToEntity(FieldCropsCreateDto fieldCropsCreateDto);
}
