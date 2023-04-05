package com.attractorschool.imurab.service.topic.impl;

import com.attractorschool.imurab.dto.topic.EditDiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.repository.DiscussionTopicRepository;
import com.attractorschool.imurab.service.topic.DiscussionTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionTopicServiceImpl implements DiscussionTopicService {
    private final DiscussionTopicRepository repository;

    @Override
    public DiscussionTopic create(DiscussionTopic discussionTopic) {
        return saveOrUpdate(discussionTopic);
    }

    @Override
    public DiscussionTopic saveOrUpdate(DiscussionTopic discussionTopic) {
        return repository.save(discussionTopic);
    }

    @Override
    public DiscussionTopic delete(DiscussionTopic discussionTopic) {
        discussionTopic.setDeleted(true);
        return saveOrUpdate(discussionTopic);
    }

    @Override
    public DiscussionTopic findById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public Page<DiscussionTopic> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<DiscussionTopic> findAllBySearch(String search, Pageable pageable) {
        return repository.findAllByTitleContaining(search, pageable);
    }

    @Override
    public DiscussionTopic lookTopic(DiscussionTopic topic) {
        topic.setViews(topic.getViews() + 1);
        return saveOrUpdate(topic);
    }

    @Override
    public DiscussionTopic edit(DiscussionTopic topic, EditDiscussionTopicDto topicDto) {
        topic.setTitle(topicDto.getTitle());
        topic.setDescription(topicDto.getDescription());

        return saveOrUpdate(topic);
    }
}
