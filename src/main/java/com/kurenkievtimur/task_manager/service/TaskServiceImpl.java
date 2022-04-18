package com.kurenkievtimur.task_manager.service;

import com.kurenkievtimur.task_manager.DTO.TaskDTO;
import com.kurenkievtimur.task_manager.DTO.UpdateTaskDTO;
import com.kurenkievtimur.task_manager.entity.Task;
import com.kurenkievtimur.task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public void saveTask(TaskDTO taskDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Task task = Task.builder()
                .type(taskDTO.getType())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .date(LocalDate.parse(taskDTO.getDate(), formatter))
                .build();
        taskRepository.save(task);
    }

    @Override
    public List<Task> findTaskByDateBetween(LocalDate start, LocalDate end) {
        return taskRepository.findByDateBetween(start, end);
    }

    @Override
    public List<Task> findTaskByDate(LocalDate date) {
        return taskRepository.findByDate(date);
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTask(UpdateTaskDTO updateTaskDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Task task = Task.builder()
                .id(updateTaskDTO.getId())
                .title(updateTaskDTO.getTitle())
                .description(updateTaskDTO.getDescription())
                .type(updateTaskDTO.getType())
                .date(LocalDate.parse(updateTaskDTO.getDate(), formatter))
                .build();
        taskRepository.save(task);
    }

    @Override
    public Task findTaskById(int id) {
        return taskRepository.findById(id);
    }
}
