package com.attractorschool.imurab.service.fieldHistory;

import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.FieldHistory;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FieldHistoryService extends BaseService<FieldHistory> {
    Page<FieldHistory> findAllByField(Pageable pageable, Field field);
}
