package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.news.NewsDto;
import com.attractorschool.imurab.dto.news.avp.NewsAvpDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.News;
import com.attractorschool.imurab.entity.NewsAvp;
import com.attractorschool.imurab.mapper.news.NewsMapper;
import com.attractorschool.imurab.mapper.news.avp.NewsAvpMapper;
import com.attractorschool.imurab.service.news.NewsService;
import com.attractorschool.imurab.service.news.avp.NewsAvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsRestController {
    private final NewsService newsService;

    private final NewsAvpService newsAvpService;
    private final NewsMapper newsMapper;

    private final NewsAvpMapper newsAvpMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<NewsDto>> getNews(@RequestParam(name = "search", required = false) String search, Pageable pageable) {
        if (nonNull(search)) {
            return ResponseEntity.ok().body(newsService.findAllBySearch(search, pageable).map(newsMapper::convertEntityToDto));
        }
        return ResponseEntity.ok().body(newsService.findAll(pageable).map(newsMapper::convertEntityToDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDto> delete(@PathVariable(value = "id") News news) {
        return ResponseEntity.ok().body(newsMapper.convertEntityToDto(newsService.delete(news)));
    }

    @GetMapping(value = "/avp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<NewsAvpDto>> getNewsAvp(@PathVariable(value = "id") AVP avp,
                                                       @RequestParam(name = "search", required = false) String search,
                                                       Pageable pageable) {
        if (nonNull(search)) {
            return ResponseEntity.ok().body(newsAvpService.findAllBySearch(search, avp, pageable).map(newsAvpMapper::convertToNewsAvpDto));
        }
        return ResponseEntity.ok().body(newsAvpService.findAllByAvp(avp, pageable).map(newsAvpMapper::convertToNewsAvpDto));
    }

    @DeleteMapping(value = "/avp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsAvpDto> delete(@PathVariable(value = "id") NewsAvp newsAvp) {
        return ResponseEntity.ok().body(newsAvpMapper.convertToNewsAvpDto(newsAvpService.delete(newsAvp)));
    }
}
