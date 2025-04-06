package org.academo.academo.repository;

import org.academo.academo.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    void save(Task task);

    Optional<UUID> getIdByTaskTitle(String title);

    List<Task> getAll();

    Optional<Task> getById(UUID taskId);

    List<Task> getAllByStudentId(UUID userId);

    List<Task> getAllByTeacherId(UUID teacherId);
}
