package com.attractorschool.imurab.mapper.news;

import com.attractorschool.imurab.dto.news.CreateNewsDto;
import com.attractorschool.imurab.dto.news.NewsDto;
import com.attractorschool.imurab.entity.News;

public interface NewsMapper {
    News convertDtoToEntity(CreateNewsDto newsDto);

    NewsDto convertEntityToDto(News news);
}
