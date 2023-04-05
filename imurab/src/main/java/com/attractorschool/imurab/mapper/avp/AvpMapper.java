package com.attractorschool.imurab.mapper.avp;

import com.attractorschool.imurab.dto.avp.AvpCreateDto;
import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.entity.AVP;

import java.util.List;

public interface AvpMapper {
    AvpUpdateDto convertEntityToDto(AVP avp);
    AvpDto convertEntityToFrontDto(AVP avp);
    List<AvpUpdateDto> convertEntityToDto(List<AVP> avp);
    AVP convertAvpCreateDtoToAvp(AvpCreateDto avpCreateDto);

}
