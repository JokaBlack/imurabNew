package com.kurenkievtimur.task_manager.service;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.DTO.UpdateTaskDTO;
import com.kurenkievtimur.task_manager.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void saveTask(TaskDTO taskDTO);
    List<Task> findTaskByDateBetween(LocalDate start, LocalDate end);
    List<Task> findTaskByDate(LocalDate date);
    void deleteTask(int id);
    void updateTask(UpdateTaskDTO updateTaskDTO);
    Task findTaskById(int id);
}
