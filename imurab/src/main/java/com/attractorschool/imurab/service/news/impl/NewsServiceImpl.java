package com.attractorschool.imurab.service.news.impl;

import com.attractorschool.imurab.dto.news.EditNewsDto;
import com.attractorschool.imurab.entity.News;
import com.attractorschool.imurab.repository.NewsRepository;
import com.attractorschool.imurab.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;

    @Override
    public News create(News news) {
        return saveOrUpdate(news);
    }

    @Override
    public News saveOrUpdate(News news) {
        return repository.save(news);
    }

    @Override
    public News delete(News news) {
        news.setDeleted(true);
        return saveOrUpdate(news);
    }

    @Override
    public News findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<News> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<News> findAllBySearch(String search, Pageable pageable) {
        return repository.findAllByTitleContaining(search, pageable);
    }

    @Override
    public News update(News news, EditNewsDto newsDto) {
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        news.setImage(nonNull(newsDto.getImage()) && isNotEmpty(newsDto.getImage().getOriginalFilename()) ?
                newsDto.getImage().getOriginalFilename() : news.getImage());
        return saveOrUpdate(news);
    }
}
