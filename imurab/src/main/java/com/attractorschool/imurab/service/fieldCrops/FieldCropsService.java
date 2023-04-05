package com.attractorschool.imurab.service.fieldCrops;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.entity.FieldCrops;
import com.attractorschool.imurab.service.BaseService;

import java.util.List;

public interface FieldCropsService extends BaseService<FieldCrops> {
    List<FieldCrops> findAll();
    FieldCrops addFieldCrops(FieldCropsCreateDto fieldCropsCreateDto);

    Boolean existByName(String fieldCropsName);
    FieldCrops updateFieldCrops(FieldCrops fieldCrops, FieldCropsUpdateDto fieldCropsUpdateDto);

    Boolean existByNameOtherFieldCrops(FieldCropsUpdateDto fieldCropsUpdateDto);
}
