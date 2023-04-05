package com.attractorschool.imurab.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateDiscussionTopicDto {
    @NotEmpty(message = "{forum.topic.title.error.notEmpty}")
    private String title;

    @NotEmpty(message = "{forum.topic.description.error.notEmpty}")
    private String description;
}
