package com.attractorschool.imurab.mapper.avp.impl;

import com.attractorschool.imurab.dto.avp.AvpCreateDto;
import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AvpMapperImpl implements AvpMapper {

    @Override
    public AvpUpdateDto convertEntityToDto(AVP avp) {
        return AvpUpdateDto.builder()
                .id(avp.getId())
                .name(avp.getName())
                .seasonStartAt(avp.getSeasonStartAt())
                .seasonEndAt(avp.getSeasonEndAt())
                .build();
    }

    @Override
    public AvpDto convertEntityToFrontDto(AVP avp) {
        return AvpDto.builder()
                .id(avp.getId())
                .name(avp.getName())
                .seasonStartAt(isNull(avp.getSeasonStartAt()) ? null : avp.getSeasonStartAt())
                .seasonEndAt(isNull(avp.getSeasonEndAt()) ? null : avp.getSeasonEndAt())
                .build();
    }

    @Override
    public List<AvpUpdateDto> convertEntityToDto(List<AVP> avp) {
        return avp.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public AVP convertAvpCreateDtoToAvp(AvpCreateDto avpCreateDto) {
        return AVP.builder()
                .name(avpCreateDto.getName())
                .build();
    }
}
