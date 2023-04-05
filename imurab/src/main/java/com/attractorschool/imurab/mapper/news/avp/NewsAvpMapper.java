package com.attractorschool.imurab.mapper.news.avp;

import com.attractorschool.imurab.dto.news.avp.CreateNewsAvpDto;
import com.attractorschool.imurab.dto.news.avp.EditNewsAvpDto;
import com.attractorschool.imurab.dto.news.avp.NewsAvpDto;
import com.attractorschool.imurab.entity.NewsAvp;

public interface NewsAvpMapper {
    NewsAvp convertToEntity(CreateNewsAvpDto newsAvpDto);

    NewsAvpDto convertToNewsAvpDto(NewsAvp newsAvp);

    EditNewsAvpDto convertToEditNewsAvpDto(NewsAvp newsAvp);
}
