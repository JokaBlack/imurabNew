package com.attractorschool.imurab.mapper.message.impl;

import com.attractorschool.imurab.dto.message.CreateMessageDto;
import com.attractorschool.imurab.dto.message.MessageDto;
import com.attractorschool.imurab.entity.Message;
import com.attractorschool.imurab.entity.User;
import com.attractorschool.imurab.mapper.message.MessageMapper;
import com.attractorschool.imurab.service.topic.DiscussionTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MessageMapperImpl implements MessageMapper {
    private final DiscussionTopicService topicService;

    @Override
    public MessageDto convertEntityToDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .createdAt(message.getCreatedAt())
                .message(message.getMessage())
                .fio(String.format("%s %s %s", message.getUser().getFirstName(), message.getUser().getLastName(),
                        message.getUser().getPatronymic()))
                .userId(message.getUser().getId())
                .build();
    }

    @Override
    public Message convertDtoToEntity(CreateMessageDto messageDto, User user) {
        return Message.builder()
                .createdAt(LocalDateTime.now())
                .message(messageDto.getMessage())
                .topic(topicService.findById(messageDto.getTopicId()))
                .user(user)
                .build();
    }
}
