package org.academo.academo.repository;

import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.Grade;
import org.academo.academo.model.Task;
import org.academo.academo.model.User;
import org.academo.academo.repository.impl.SubmissionRepositoryImpl;
import org.academo.academo.repository.impl.TaskRepositoryImpl;
import org.academo.academo.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@ExtendWith(TestWatcherExtension.class)
@DisplayName("Task Repository Integration Tests")
public class TaskRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private TaskRepositoryImpl taskRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User student;
    private User teacher;
    private Task taskToSave;


    @BeforeEach
    void setUp() {
        student = new User("stud", "pass", "studF", "student");
        teacher = new User("teacher", "pow", "teacherF", "teacher");
        userRepository.saveUser(teacher);
        teacher.setId(userRepository.getIdByUserName(teacher.getUsername()).get());
        userRepository.saveUser(student);
        student.setId(userRepository.getIdByUserName(student.getUsername()).get());
        taskToSave = new Task("titleTest", "descriptionTest", student.getId(), teacher.getId());
        taskRepository.save(taskToSave);
        taskToSave.setId(taskRepository.getIdByTaskTitle(taskToSave.getTitle()).get());
    }


    @Test
    @DisplayName("Get All Tasks - Should return all tasks from test data")
    void getAll_ShouldReturnAllTasks() {
        List<Task> tasks = taskRepository.getAll();

        assertThat(tasks)
                .hasSize(1);
    }

    @Test
    @DisplayName("Get Task By ID - Should return task when exists")
    void getById_ShouldReturnTaskWhenExists() {
        Optional<Task> task = taskRepository.getById(taskToSave.getId());
        assertThat(task.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Get Tasks By Student ID - Should return student's tasks")
    void getAllByUserId_ShouldReturnStudentsTasks() {
        List<Task> tasks = taskRepository.getAllByStudentId(student.getId());

        assertThat(tasks)
                .hasSize(1);
    }

    @Test
    @DisplayName("Get Tasks By Teacher ID - Should return teacher's tasks")
    void getAllByTeacherId_ShouldReturnTeachersTasks() {
        List<Task> tasks = taskRepository.getAllByTeacherId(teacher.getId());

        assertThat(tasks)
                .hasSize(1);
    }

    @Test
    @DisplayName("Get Task By ID - Should throw exception when not found")
    void getById_ShouldThrowWhenNotFound() {
        // Act & Assert
        assertThatExceptionOfType(org.springframework.dao.EmptyResultDataAccessException.class)
                .isThrownBy(() -> taskRepository.getById(UUID.randomUUID()));
    }
}