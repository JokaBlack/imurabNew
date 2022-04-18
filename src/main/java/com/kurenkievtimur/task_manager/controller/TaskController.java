package com.kurenkievtimur.task_manager.controller;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.DTO.UpdateTaskDTO;
import com.kurenkievtimur.task_manager.entity.Task;
import com.kurenkievtimur.task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("{year}/{month}")
public class TaskController {
    private final TaskService taskService;
    private LocalDate currentDate = LocalDate.now();

    @GetMapping()
    public String showAllTasks(Model model, @PathVariable int year, @PathVariable int month) {
        currentDate = currentDate.withYear(year).withMonth(month).withDayOfMonth(1);

        model.addAttribute("currentDate", currentDate);
        model.addAttribute("tasks", taskService.findTaskByDateBetween(currentDate, currentDate.plusMonths(1).minusDays(1)));

        return "index";
    }

    @GetMapping("/{day}")
    public String showTasksDay(Model model, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
        currentDate = currentDate.withDayOfMonth(day);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("tasks", taskService.findTaskByDate(LocalDate.of(year, month, day)));
        return "tasks";
    }

    @GetMapping("/{day}/add")
    public String addTask(Model model, @PathVariable int day) {
        currentDate.withDayOfMonth(day);
        model.addAttribute("currentDate", currentDate);
        return "addTask";
    }

    @PostMapping("/{day}/add")
    public String addTask(TaskDTO taskDTO, @PathVariable int day) {
        taskService.saveTask(taskDTO);
        return "redirect:/{year}/{month}/{day}";
    }

    @GetMapping("/{day}/delete")
    public String deleteTask(@PathVariable int day, @RequestParam int id) {
        taskService.deleteTask(id);
        return "redirect:/{year}/{month}/{day}";
    }

    @GetMapping("/{day}/update")
    public String updateTask(Model model, @PathVariable int day, @RequestParam int id) {
        currentDate.withDayOfMonth(day);
        Task task = taskService.findTaskById(id);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("task", task);
        return "updateTask";
    }

    @PostMapping("/{day}/update")
    public String updateTask(UpdateTaskDTO updateTaskDTO, @PathVariable int day) {
        taskService.updateTask(updateTaskDTO);
        return "redirect:/{year}/{month}/{day}";
    }
}
