package com.attractorschool.imurab.service.news.avp.impl;

import com.attractorschool.imurab.dto.news.avp.EditNewsAvpDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.NewsAvp;
import com.attractorschool.imurab.repository.NewsAvpRepository;
import com.attractorschool.imurab.service.avp.AvpService;
import com.attractorschool.imurab.service.news.avp.NewsAvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Service
@RequiredArgsConstructor
public class NewsAvpServiceImpl implements NewsAvpService {
    private final NewsAvpRepository repository;

    private final AvpService avpService;


    @Override
    public NewsAvp create(NewsAvp newsAvp) {
        return saveOrUpdate(newsAvp);
    }

    @Override
    public NewsAvp saveOrUpdate(NewsAvp newsAvp) {
        return repository.save(newsAvp);
    }

    @Override
    public NewsAvp delete(NewsAvp newsAvp) {
        newsAvp.setDeleted(true);
        return saveOrUpdate(newsAvp);
    }

    @Override
    public NewsAvp findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public NewsAvp update(NewsAvp newsAvp, EditNewsAvpDto newsAvpDto) {
        newsAvp.setTitle(newsAvpDto.getTitle());
        newsAvp.setText(newsAvp.getText());
        newsAvp.setAvp(avpService.findById(newsAvpDto.getAvpId()));
        newsAvp.setImage(nonNull(newsAvpDto.getImage()) && isNotEmpty(newsAvpDto.getImage().getOriginalFilename()) ?
                newsAvpDto.getImage().getOriginalFilename() : newsAvp.getImage());

        return saveOrUpdate(newsAvp);
    }

    @Override
    public Page<NewsAvp> findAllByAvp(AVP avp, Pageable pageable) {
        return repository.findAllByAvp(avp, pageable);
    }

    @Override
    public Page<NewsAvp> findAllBySearch(String search, AVP avp, Pageable pageable) {
        return repository.findAllByTitleContainingAndAvp(search, avp, pageable);
    }
}
