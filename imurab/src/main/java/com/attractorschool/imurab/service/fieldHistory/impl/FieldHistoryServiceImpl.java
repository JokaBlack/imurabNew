package com.attractorschool.imurab.service.fieldHistory.impl;

import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.FieldHistory;
import com.attractorschool.imurab.repository.FieldHistoryRepository;
import com.attractorschool.imurab.service.fieldHistory.FieldHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldHistoryServiceImpl implements FieldHistoryService {
    private final FieldHistoryRepository repository;

    @Override
    public FieldHistory create(FieldHistory fieldHistory) {
        return saveOrUpdate(fieldHistory);
    }

    @Override
    public FieldHistory saveOrUpdate(FieldHistory fieldHistory) {
        return repository.save(fieldHistory);
    }

    @Override
    public FieldHistory delete(FieldHistory fieldHistory) {
        repository.delete(fieldHistory);
        return fieldHistory;
    }

    @Override
    public FieldHistory findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<FieldHistory> findAllByField(Pageable pageable, Field field) {
        return repository.findAllByField(pageable, field);
    }
}
