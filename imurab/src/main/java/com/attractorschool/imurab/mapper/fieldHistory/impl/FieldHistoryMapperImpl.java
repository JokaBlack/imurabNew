package com.attractorschool.imurab.mapper.fieldHistory.impl;

import com.attractorschool.imurab.dto.fieldHistory.CreateFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.EditFieldHistoryDto;
import com.attractorschool.imurab.dto.fieldHistory.FieldHistoryDto;
import com.attractorschool.imurab.entity.FieldHistory;
import com.attractorschool.imurab.mapper.field.FieldMapper;
import com.attractorschool.imurab.mapper.fieldHistory.FieldHistoryMapper;
import com.attractorschool.imurab.service.field.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FieldHistoryMapperImpl implements FieldHistoryMapper {
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @Override
    public FieldHistory convertDtoToEntity(CreateFieldHistoryDto fieldDto) {
        return FieldHistory.builder()
                .startedAt(fieldDto.getStartedAt())
                .endedAt(fieldDto.getEndedAt())
                .description(fieldDto.getDescription())
                .field(fieldService.findById(fieldDto.getFieldId()))
                .build();
    }

    @Override
    public FieldHistory convertDtoToEntity(EditFieldHistoryDto fieldHistoryDto) {
        return FieldHistory.builder()
                .id(fieldHistoryDto.getId())
                .startedAt(fieldHistoryDto.getStartedAt())
                .endedAt(fieldHistoryDto.getEndedAt())
                .field(fieldService.findById(fieldHistoryDto.getFieldId()))
                .description(fieldHistoryDto.getDescription())
                .build();
    }

    @Override
    public FieldHistoryDto convertEntityToDto(FieldHistory fieldHistory) {
        return FieldHistoryDto.builder()
                .id(fieldHistory.getId())
                .startedAt(fieldHistory.getStartedAt())
                .endedAt(fieldHistory.getEndedAt())
                .fieldDto(fieldMapper.convertToFieldDto(fieldHistory.getField()))
                .description(fieldHistory.getDescription())
                .build();
    }
}
