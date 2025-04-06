package org.academo.academo.service;

import org.academo.academo.dto.TaskDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    void createTask(TaskDTO task);

    List<TaskDTO> getAllTasks();

    TaskDTO getById(UUID taskId);
    List<TaskDTO> getAllByUserId(UUID userId);
    List<TaskDTO> getAllByTeacherId(UUID teacherId);
}
