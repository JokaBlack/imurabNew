package com.attractorschool.imurab.service.topic;

import com.attractorschool.imurab.dto.topic.EditDiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscussionTopicService extends BaseService<DiscussionTopic> {
    Page<DiscussionTopic> findAll(Pageable pageable);

    Page<DiscussionTopic> findAllBySearch(String search, Pageable pageable);

    DiscussionTopic lookTopic(DiscussionTopic topic);

    DiscussionTopic edit(DiscussionTopic topic, EditDiscussionTopicDto topicDto);
}
