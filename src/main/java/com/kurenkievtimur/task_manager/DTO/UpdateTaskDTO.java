package com.kurenkievtimur.task_manager.DTO;

import com.kurenkievtimur.task_manager.entity.TaskType;
import lombok.Data;

@Data
public class UpdateTaskDTO {
    Integer id;
    String title;
    String description;
    TaskType type;
    String date;
}
