package com.attractorschool.imurab.service.news.avp;

import com.attractorschool.imurab.dto.news.avp.EditNewsAvpDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.NewsAvp;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsAvpService extends BaseService<NewsAvp> {
    NewsAvp update(NewsAvp newsAvp, EditNewsAvpDto newsAvpDto);

    Page<NewsAvp> findAllByAvp(AVP avp, Pageable pageable);

    Page<NewsAvp> findAllBySearch(String search, AVP avp, Pageable pageable);
}
