package com.kurenkievtimur.task_manager.controller;

import com.kurenkievtimur.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public String showTasks() {
        return "index";
    }
}
