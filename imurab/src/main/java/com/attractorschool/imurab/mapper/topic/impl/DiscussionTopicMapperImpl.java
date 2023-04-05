package com.attractorschool.imurab.mapper.topic.impl;

import com.attractorschool.imurab.dto.topic.CreateDiscussionTopicDto;
import com.attractorschool.imurab.dto.topic.DiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.topic.DiscussionTopicMapper;
import com.attractorschool.imurab.util.enums.TopicStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiscussionTopicMapperImpl implements DiscussionTopicMapper {
    @Override
    public DiscussionTopic convertDtoToEntity(CreateDiscussionTopicDto topicDto, User user) {
        return DiscussionTopic.builder()
                .title(topicDto.getTitle())
                .description(topicDto.getDescription())
                .createdAt(LocalDateTime.now())
                .status(TopicStatus.OPENED)
                .user(user)
                .build();
    }

    @Override
    public DiscussionTopicDto convertEntityToDto(DiscussionTopic topic) {
        return DiscussionTopicDto.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .fio(String.format("%s %s %s", topic.getUser().getFirstName(), topic.getUser().getLastName(),
                        topic.getUser().getPatronymic()))
                .views(topic.getViews())
                .messages(topic.getMessages())
                .createdAt(topic.getCreatedAt())
                .build();
    }
}
