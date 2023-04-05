package com.attractorschool.imurab.mapper.fieldCrops.impl;

import com.attractorschool.imurab.dto.fieldCrops.FieldCropsCreateDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsDto;
import com.attractorschool.imurab.dto.fieldCrops.FieldCropsUpdateDto;
import com.attractorschool.imurab.entity.FieldCrops;
import com.attractorschool.imurab.mapper.fieldCrops.FieldCropsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FieldCropsMapperImpl implements FieldCropsMapper {
    @Override
    public FieldCropsDto convertEntityDto(FieldCrops fieldCrops) {
        return FieldCropsDto.builder()
                .id(fieldCrops.getId())
                .name(fieldCrops.getName())
                .coefficient(fieldCrops.getCoefficient())
                .imgLink(fieldCrops.getImgLink())
                .build();
    }

    @Override
    public FieldCropsUpdateDto convertEntityToFieldCropsUpdateDto(FieldCrops fieldCrops) {
        return FieldCropsUpdateDto.builder()
                .id(fieldCrops.getId())
                .name(fieldCrops.getName())
                .coefficient(fieldCrops.getCoefficient())
                .build();
    }

    @Override
    public List<FieldCropsDto> convertEntityToDto(List<FieldCrops> fieldCrops) {
        return fieldCrops.stream().map(this::convertEntityDto).collect(Collectors.toList());
    }

    @Override
    public FieldCrops convertFieldCropsCreateDtoToEntity(FieldCropsCreateDto fieldCropsCreateDto) {
        return FieldCrops.builder()
                .name(fieldCropsCreateDto.getName().strip())
                .coefficient(fieldCropsCreateDto.getCoefficient())
                .imgLink(fieldCropsCreateDto.getMultipartFile().getOriginalFilename())
                .build();
    }
}
