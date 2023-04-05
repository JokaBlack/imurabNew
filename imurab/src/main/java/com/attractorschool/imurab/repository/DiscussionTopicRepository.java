package com.attractorschool.imurab.repository;

import com.attractorschool.imurab.entity.DiscussionTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopic, Long> {
    Page<DiscussionTopic> findAllByTitleContaining(String search, Pageable pageable);
}
