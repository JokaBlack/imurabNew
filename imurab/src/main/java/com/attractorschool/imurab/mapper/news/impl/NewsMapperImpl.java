package com.attractorschool.imurab.mapper.news.impl;

import com.attractorschool.imurab.dto.news.CreateNewsDto;
import com.attractorschool.imurab.dto.news.NewsDto;
import com.attractorschool.imurab.entity.News;
import com.attractorschool.imurab.mapper.news.NewsMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Component
public class NewsMapperImpl implements NewsMapper {
    @Override
    public News convertDtoToEntity(CreateNewsDto newsDto) {
        return News.builder()
                .title(newsDto.getTitle())
                .image(nonNull(newsDto.getImage()) && Strings.isNotEmpty(newsDto.getImage().getOriginalFilename()) ?
                        newsDto.getImage().getOriginalFilename() : "default-image.jpg")
                .text(newsDto.getText())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public NewsDto convertEntityToDto(News news) {
        return NewsDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .image(news.getImage())
                .text(news.getText())
                .createdAt(news.getCreatedAt())
                .build();
    }
}
