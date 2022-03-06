package com.kurenkievtimur.task_manager.controller;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private LocalDate currentDate = LocalDate.now();

    @GetMapping("/")
    public String showAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("currentDate", currentDate);
        return "index";
    }

    @GetMapping("/next")
    public String nextMonth(Model model) {
        currentDate = currentDate.plusMonths(1);
        model.addAttribute("currentDate", currentDate);
        return "redirect:/";
    }

    @GetMapping("/prev")
    public String prevMonth(Model model) {
        currentDate = currentDate.minusMonths(1);
        model.addAttribute("currentDate", currentDate);
        return "redirect:/";
    }

    @GetMapping("/tasks")
    public String showTasksDay() {
        return "tasks";
    }

    @GetMapping("/tasks/add")
    public String addTask() {
        return "addTask";
    }

    @PostMapping("tasks/add")
    public String addTask(TaskDTO taskDTO) {
        taskService.save(taskDTO);
        return "redirect:/tasks";
    }


}
