package com.attractorschool.imurab.mapper.topic;

import com.attractorschool.imurab.dto.topic.CreateDiscussionTopicDto;
import com.attractorschool.imurab.dto.topic.DiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.entity.User;

public interface DiscussionTopicMapper {
    DiscussionTopic convertDtoToEntity(CreateDiscussionTopicDto topicDto, User user);

    DiscussionTopicDto convertEntityToDto(DiscussionTopic topic);
}
