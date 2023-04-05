package com.attractorschool.imurab.controller.rest;

import com.attractorschool.imurab.dto.message.CreateMessageDto;
import com.attractorschool.imurab.dto.message.EditMessageDto;
import com.attractorschool.imurab.dto.message.MessageDto;
import com.attractorschool.imurab.dto.topic.DiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.entity.Message;
import com.attractorschool.imurab.mapper.message.MessageMapper;
import com.attractorschool.imurab.mapper.topic.DiscussionTopicMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.message.MessageService;
import com.attractorschool.imurab.service.topic.DiscussionTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/discussion-topics")
@RequiredArgsConstructor
public class DiscussionTopicRestController {
    private final DiscussionTopicService topicService;
    private final DiscussionTopicMapper topicMapper;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DiscussionTopicDto>> getTopics(@RequestParam(name = "page") int page,
                                                              @RequestParam("orderBy") String orderBy,
                                                              @RequestParam("order") String order,
                                                              @RequestParam(value = "search", required = false) String search) {
        if (nonNull(search)) {
            return ResponseEntity.ok().body(topicService.findAllBySearch(search, PageRequest.of(page, 10,
                    Sort.by(Sort.Direction.fromString(order), orderBy))).map(topicMapper::convertEntityToDto));
        }
        return ResponseEntity.ok().body(topicService.findAll(PageRequest.of(page, 10,
                Sort.by(Sort.Direction.fromString(order), orderBy))).map(topicMapper::convertEntityToDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiscussionTopicDto> delete(@PathVariable(value = "id") DiscussionTopic topic) {
        return ResponseEntity.ok().body(topicMapper.convertEntityToDto(topicService.delete(topic)));
    }

    @GetMapping(value = "/{id}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MessageDto>> getMessages(@RequestParam(name = "page") int page,
                                                        @PathVariable(value = "id") DiscussionTopic topic) {
        topicService.lookTopic(topic);
        return ResponseEntity.ok().body(messageService.findByDiscussionTopic(PageRequest.of(page, 10, Sort.by("id").descending()), topic)
                .map(messageMapper::convertEntityToDto));
    }

    @DeleteMapping(value = "/messages/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> deleteMessage(@PathVariable(value = "messageId") Message message) {
        message.getTopic().setMessages(message.getTopic().getMessages() - 1);
        return ResponseEntity.ok().body(messageMapper.convertEntityToDto(messageService.delete(message)));
    }

    @PutMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> editMessage(@PathVariable(value = "id") Message message, @RequestBody @Valid EditMessageDto messageDto) {
        message.setMessage(messageDto.getMessage());
        return ResponseEntity.ok().body(messageMapper.convertEntityToDto(messageService.saveOrUpdate(message)));
    }

    @GetMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> getMessage(@PathVariable(value = "id") Message message) {
        return ResponseEntity.ok().body(messageMapper.convertEntityToDto(message));
    }

    @PostMapping(value = "/messages/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> createMessage(@RequestBody @Valid CreateMessageDto messageDto,
                                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Message message = messageMapper.convertDtoToEntity(messageDto, userPrincipal.getUser());
        return ResponseEntity.ok().body(messageMapper.convertEntityToDto(messageService.create(message)));
    }
}
