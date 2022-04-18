package com.kurenkievtimur.task_manager.repository;

import com.kurenkievtimur.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByDateBetween(LocalDate start, LocalDate end);

    List<Task> findByDate(LocalDate date);

    Task findById(int id);
}
