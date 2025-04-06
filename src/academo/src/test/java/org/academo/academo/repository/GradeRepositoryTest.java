package org.academo.academo.repository;

import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.Grade;
import org.academo.academo.model.Submission;
import org.academo.academo.model.Task;
import org.academo.academo.model.User;
import org.academo.academo.repository.impl.GradeRepositoryImpl;
import org.academo.academo.repository.impl.SubmissionRepositoryImpl;
import org.academo.academo.repository.impl.TaskRepositoryImpl;
import org.academo.academo.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@ExtendWith(TestWatcherExtension.class)
@DisplayName("Grade Repository Integration Tests")
public class GradeRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private GradeRepositoryImpl gradeRepository;
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

    
    //TODO: Database generates the ID but hte UUID function, so i have to get ID from db, adn assign it to the model that i use in the test.
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
//        gradeToSave = new Grade(5.3, "FeedBackTest", submissionToSave.getId());
//        gradeRepository.save(gradeToSave);
    }

    @Test
    @DisplayName("Save grade -- Should persist grade when valid data provided")
    void saveGrade_ShouldPersistGradeWhenValidDataProvided() {

        Grade grade = new Grade(5.3, "FeedBackTest", submissionToSave.getId());

        gradeRepository.save(grade);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM grade WHERE submission_id = ? AND feedback = ?",
                Integer.class, submissionToSave.getId(), "FeedBackTest"
        );

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Get grade by submission id -- Should return grade when exists ")
    void getBySubmissionId_shouldReturnGrade_whenExists() {

        Grade gradeToSave = new Grade(5.3, "FeedBackTest", submissionToSave.getId());
        gradeRepository.save(gradeToSave);

        Optional<Grade> grade = gradeRepository.getBySubmissionId(submissionToSave.getId());

        assertThat(grade).isPresent();
    }

    @Test
    @DisplayName("Get grade by id -- Should return empty when not found")
    void getById_shouldReturnEmpty_whenNotFound() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> gradeRepository.getById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Get all grades -- Should return all grades")
    void getAll_shouldReturnAllGrades() {
        List<Grade> grades = gradeRepository.getAll();
        assertThat(grades).isNotNull();
    }

}
