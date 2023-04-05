package com.attractorschool.imurab.service.news;

import com.attractorschool.imurab.dto.news.EditNewsDto;
import com.attractorschool.imurab.entity.News;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService extends BaseService<News> {
    Page<News> findAll(Pageable pageable);

    Page<News> findAllBySearch(String search, Pageable pageable);

    News update(News news, EditNewsDto newsDto);
}
