package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.FieldCrops;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldCropsRepository extends JpaRepository<FieldCrops, Long> {
    @Override
    Page<FieldCrops> findAll(Pageable pageable);
}
