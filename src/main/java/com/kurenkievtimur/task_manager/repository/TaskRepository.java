package com.kurenkievtimur.task_manager.repository;

import com.kurenkievtimur.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
