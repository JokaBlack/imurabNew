package com.kurenkievtimur.task_manager.service;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.entity.Task;
import com.kurenkievtimur.task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public void save(TaskDTO taskDTO) {
        Task task = Task.builder()
                .type(taskDTO.getType())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .date(LocalDateTime.now())
                .build();
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
