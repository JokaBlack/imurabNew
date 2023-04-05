package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.AVP;
import com.attractorschool.imurab.entity.NewsAvp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsAvpRepository extends JpaRepository<NewsAvp, Long> {
    Page<NewsAvp> findAllByAvp(AVP avp, Pageable pageable);

    Page<NewsAvp> findAllByTitleContainingAndAvp(String search, AVP avp, Pageable pageable);
}
