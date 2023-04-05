package com.attractorschool.imurab.service.message;

import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.entity.Message;
import com.attractorschool.imurab.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService extends BaseService<Message> {
    Page<Message> findByDiscussionTopic(Pageable pageable, DiscussionTopic topic);
}
