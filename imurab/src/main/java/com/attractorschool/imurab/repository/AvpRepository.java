package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvpRepository extends JpaRepository<AVP, Long> {
    @Override
    Page<AVP> findAll(Pageable pageable);
}
