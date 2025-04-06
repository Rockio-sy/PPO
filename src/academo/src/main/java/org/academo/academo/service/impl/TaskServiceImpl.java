package org.academo.academo.service.impl;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.TaskDTO;
import org.academo.academo.model.Task;
import org.academo.academo.repository.TaskRepository;
import org.academo.academo.service.TaskService;
import org.academo.academo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Converter converter;

    @Override
    public void createTask(TaskDTO task) {
        try {
            Task model = converter.dtoToTask(task);
            taskRepository.save(model);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Task with this identifier already exists", e);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Missing or invalid task data", e);
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to create task due to database error", e);
        }
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        try {
            List<Task> tasks = taskRepository.getAll();
            return tasks.stream()
                    .map(converter::taskToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch tasks", e);
        }
    }

    @Override
    public List<TaskDTO> getAllByTeacherId(UUID teacherId) {
        try {
            List<Task> tasks = taskRepository.getAllByTeacherId(teacherId);
            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("No tasks found for teacher ID: " + teacherId);
            }
            return tasks.stream()
                    .map(converter::taskToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch tasks by teacher ID", e);
        }
    }

    @Override
    public TaskDTO getById(UUID taskId) {
        try {
            Optional<Task> task = taskRepository.getById(taskId);
            return task.map(converter::taskToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch task by ID", e);
        }
    }

    @Override
    public List<TaskDTO> getAllByUserId(UUID userId) {
        try {
            List<Task> tasks = taskRepository.getAllByStudentId(userId);
            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("No tasks found for user ID: " + userId);
            }
            return tasks.stream()
                    .map(converter::taskToDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch tasks by user ID", e);
        }
    }
}
