package com.attractorschool.imurab.controller.view;

import com.attractorschool.imurab.dto.topic.CreateDiscussionTopicDto;
import com.attractorschool.imurab.dto.topic.EditDiscussionTopicDto;
import com.attractorschool.imurab.entity.DiscussionTopic;
import com.attractorschool.imurab.mapper.topic.DiscussionTopicMapper;
import com.attractorschool.imurab.security.UserPrincipal;
import com.attractorschool.imurab.service.topic.DiscussionTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumController {
    private final DiscussionTopicService discussionTopicService;
    private final DiscussionTopicMapper discussionTopicMapper;

    @GetMapping
    public ModelAndView forumPage() {
        return new ModelAndView("forum/index");
    }

    @GetMapping("/discussion-topic/create")
    public ModelAndView createTopicPage(@ModelAttribute("topicDto") CreateDiscussionTopicDto topicDto) {
        return new ModelAndView("forum/createDiscussionTopic");
    }

    @PostMapping("/discussion-topic/create")
    public ModelAndView createTopic(@AuthenticationPrincipal UserPrincipal principal,
                                    @ModelAttribute("topicDto") @Valid CreateDiscussionTopicDto topicDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("forum/createDiscussionTopic", result.getModel());
        }
        discussionTopicService.create(discussionTopicMapper.convertDtoToEntity(topicDto, principal.getUser()));
        return new ModelAndView("redirect:/forum");
    }

    @GetMapping("/discussion-topic/{id}")
    public ModelAndView getTopicPage(@PathVariable(value = "id") DiscussionTopic topic) {
        return new ModelAndView("forum/discussionTopic")
                .addObject("topic", discussionTopicMapper.convertEntityToDto(topic));
    }

    @GetMapping("/discussion-topic/{id}/edit")
    public ModelAndView editTopicPage(@PathVariable(value = "id") DiscussionTopic topic) {
        return new ModelAndView("forum/edit")
                .addObject("topicDto", discussionTopicMapper.convertEntityToDto(topic));
    }

    @PutMapping("/discussion-topic/{id}")
    public ModelAndView editTopic(@PathVariable(value = "id") DiscussionTopic topic,
                                  @ModelAttribute("topicDto") @Valid EditDiscussionTopicDto topicDto,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("forum/edit").addObject(result.getModel());
        }
        discussionTopicService.edit(topic, topicDto);
        return new ModelAndView("redirect:/forum");
    }
}
