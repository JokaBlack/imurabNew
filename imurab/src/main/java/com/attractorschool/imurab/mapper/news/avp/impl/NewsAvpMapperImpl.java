package com.attractorschool.imurab.mapper.news.avp.impl;

import com.attractorschool.imurab.dto.news.avp.CreateNewsAvpDto;
import com.attractorschool.imurab.dto.news.avp.EditNewsAvpDto;
import com.attractorschool.imurab.dto.news.avp.NewsAvpDto;
import com.attractorschool.imurab.entity.NewsAvp;
import com.attractorschool.imurab.mapper.avp.AvpMapper;
import com.attractorschool.imurab.mapper.news.avp.NewsAvpMapper;
import com.attractorschool.imurab.service.avp.AvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Component
@RequiredArgsConstructor
public class NewsAvpMapperImpl implements NewsAvpMapper {
    private final AvpService avpService;
    private final AvpMapper avpMapper;

    @Override
    public NewsAvp convertToEntity(CreateNewsAvpDto newsAvpDto) {
        return NewsAvp.builder()
                .title(newsAvpDto.getTitle())
                .text(newsAvpDto.getText())
                .image(nonNull(newsAvpDto.getImage()) && isNotEmpty(newsAvpDto.getImage().getOriginalFilename()) ?
                        newsAvpDto.getImage().getOriginalFilename() : "default_image.jpg")
                .avp(avpService.findById(newsAvpDto.getAvpId()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public NewsAvpDto convertToNewsAvpDto(NewsAvp newsAvp) {
        return NewsAvpDto.builder()
                .id(newsAvp.getId())
                .title(newsAvp.getTitle())
                .text(newsAvp.getText())
                .image(newsAvp.getImage())
                .avpDto(avpMapper.convertEntityToFrontDto(newsAvp.getAvp()))
                .createdAt(newsAvp.getCreatedAt())
                .build();
    }

    @Override
    public EditNewsAvpDto convertToEditNewsAvpDto(NewsAvp newsAvp) {
        return EditNewsAvpDto.builder()
                .id(newsAvp.getId())
                .title(newsAvp.getTitle())
                .text(newsAvp.getText())
                .avpId(newsAvp.getAvp().getId())
                .build();
    }
}
