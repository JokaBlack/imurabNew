package com.attractorschool.imurab.service.avp.impl;

import com.attractorschool.imurab.dto.avp.AvpDto;
import com.attractorschool.imurab.dto.avp.AvpUpdateDto;
import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.repository.AvpRepository;
import com.attractorschool.imurab.service.avp.AvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvpServiceImpl implements AvpService {
    private final AvpRepository repository;

    @Override
    public AVP setSeasonStart(AvpUpdateDto avpUpdateDto) {
        AVP updatedAvp = findById(avpUpdateDto.getId());
        updatedAvp.setSeasonStartAt(avpUpdateDto.getNewDate());
        return saveOrUpdate(updatedAvp);
    }

    @Override
    public AVP setSeasonEnd(AvpUpdateDto avpUpdateDto) {
        AVP updatedAvp = findById(avpUpdateDto.getId());
        updatedAvp.setSeasonEndAt(avpUpdateDto.getNewDate());
        return saveOrUpdate(updatedAvp);
    }

    @Override
    public Boolean existByName(String name) {
        List<AVP> avpList = findAll();
        return avpList.stream().anyMatch(e -> e.getName().equalsIgnoreCase(name));
    }

    @Override
    public AVP updateAvp(AVP avp, AvpDto avpDto) {
        avp.setName(avpDto.getName());
        avp.setSeasonStartAt(avpDto.getSeasonStartAt());
        avp.setSeasonEndAt(avpDto.getSeasonEndAt());
        return saveOrUpdate(avp);
    }

    @Override
    public List<AVP> findAll() {
        return repository.findAll();
    }

    public Page<AVP> findAvpPage(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Override
    public AVP create(AVP avp) {
        return saveOrUpdate(avp);
    }

    @Override
    public AVP saveOrUpdate(AVP avp) {
        return repository.saveAndFlush(avp);
    }

    @Override
    public AVP delete(AVP avp) {
        avp.setDeleted(true);
        return saveOrUpdate(avp);
    }

    @Override
    public AVP findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
