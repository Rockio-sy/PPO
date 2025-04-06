package org.academo.academo.repository;


import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.Grade;
import org.academo.academo.model.Submission;
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
@DisplayName("Submission Repository Integration Tests")
public class SubmissionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private TaskRepositoryImpl taskRepository;
    @Autowired
    private SubmissionRepositoryImpl submissionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User student;
    private User teacher;
    private Task task;
    private Submission submissionToSave;
    private Grade gradeToSave;


    @BeforeEach
    void setUp() {
        student = new User("stud", "pass", "studF", "student");
        teacher = new User("teacher", "pow", "teacherF", "teacher");
        userRepository.saveUser(teacher);
        teacher.setId(userRepository.getIdByUserName(teacher.getUsername()).get());
        userRepository.saveUser(student);
        student.setId(userRepository.getIdByUserName(student.getUsername()).get());
        task = new Task("titleTest", "descriptionTest", student.getId(), teacher.getId());
        taskRepository.save(task);
        task.setId(taskRepository.getIdByTaskTitle(task.getTitle()).get());
        submissionToSave = new Submission(teacher.getId(), student.getId(), task.getId(), "Answer");
        submissionRepository.save(submissionToSave);
        submissionToSave.setId(submissionRepository.getIdByTaskId(submissionToSave.getTaskId()).get());
    }


    @Test
    @DisplayName("Get All Submissions - Should retrieve all submissions")
    void getAll_ShouldReturnAllSubmissions() {
        List<Submission> submissions = submissionRepository.getAll();

        assertThat(submissions)
                .hasSize(1);
    }

    @Test
    @DisplayName("Get By Task ID - Should return submission when exists")
    void getByTaskId_ShouldReturnSubmissionWhenExists() {
        Optional<Submission> submission = submissionRepository.getByTaskId(task.getId());

        assertThat(submission)
                .isPresent()
                .hasValueSatisfying(s ->
                        assertThat(s.getTaskId()).isEqualTo(task.getId()));
    }

    @Test
    @DisplayName("Get By Task ID - Should throw exception when not found")
    void getByTaskId_ShouldThrowWhenNotFound() {
        assertThatExceptionOfType(org.springframework.dao.EmptyResultDataAccessException.class)
                .isThrownBy(() -> submissionRepository.getByTaskId(UUID.randomUUID()));
    }
}