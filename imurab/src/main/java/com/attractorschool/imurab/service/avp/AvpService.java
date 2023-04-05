package com.attractorschool.imurab.service.avp;

import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvpService extends BaseService<AVP> {
    AVP setSeasonStart(AvpUpdateDto avpUpdateDto);

    AVP setSeasonEnd(AvpUpdateDto avpUpdateDto);

    Boolean existByName(String name);

    AVP updateAvp(AVP avp, AvpDto avpDto);

    List<AVP> findAll();

    Page<AVP> findAvpPage(Pageable pageable);
}
