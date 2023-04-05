package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.Field;
import com.attractorschool.imurab.entity.FieldHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldHistoryRepository extends JpaRepository<FieldHistory, Long> {
    Page<FieldHistory> findAllByField(Pageable pageable, Field field);
}
