package com.kurenkievtimur.task_manager.DTO;


import com.kurenkievtimur.task_manager.entity.TaskType;
import lombok.Data;

@Data
public class TaskDTO {
    TaskType type;
    String title;
    String description;
}
