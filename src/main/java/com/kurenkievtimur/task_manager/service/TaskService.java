package com.kurenkievtimur.task_manager.service;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.entity.Task;

import java.util.List;

public interface TaskService {
    void save(TaskDTO taskDTO);
    List<Task> findAll();
}
