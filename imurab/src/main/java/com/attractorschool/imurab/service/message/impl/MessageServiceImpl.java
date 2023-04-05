package com.attractorschool.imurab.service.message.impl;

import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.entity.Message;
import com.attractorschool.imurab.repository.MessageRepository;
import com.attractorschool.imurab.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository repository;

    @Override
    public Message create(Message message) {
        message.getTopic().setMessages(message.getTopic().getMessages() + 1);
        return saveOrUpdate(message);
    }

    @Override
    public Message saveOrUpdate(Message message) {
        return repository.save(message);
    }

    @Override
    public Message delete(Message message) {
        message.setDeleted(true);
        return saveOrUpdate(message);
    }

    @Override
    public Message findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<Message> findByDiscussionTopic(Pageable pageable, DiscussionTopic topic) {
        return repository.findAllByTopic(pageable, topic);
    }
}
