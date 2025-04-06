package org.academo.academo.repository;

import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.User;
import org.academo.academo.repository.impl.TaskRepositoryImpl;
import org.academo.academo.repository.impl.UserRepositoryImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(TestWatcherExtension.class)
@DisplayName("User repository integration tests")
public class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private TaskRepositoryImpl taskRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;


    @BeforeEach
    void setUp() {
        user = new User("stud", "pass", "studF", "student");
        userRepository.saveUser(user);
        user.setId(userRepository.getIdByUserName(user.getUsername()).get());
    }

    @Test
    @DisplayName("Save User -- Should save user successfully when valid data provided")
    void saveUser_ShouldSaveUserSuccessfullyWhenValidDataProvided() {
        User newUser = new User("usernameTest", "passwordTest", "fullNameTest", "student");

        userRepository.saveUser(newUser);

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users", Integer.class);
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Remove User -- Should remove user successfully when valid user ID provided")
    void removeUser_ShouldRemoveUserSuccessfullyWhenValidUserIDProvided() {
        userRepository.removeUser(user.getId());

        Timestamp removed_at = jdbcTemplate.queryForObject(
                "SELECT removed_at FROM users WHERE id = ?",
                Timestamp.class, user.getId()
        );
        assertThat(removed_at).isNotNull();
    }

    @Test
    @DisplayName("Get all users -- Should retrieve all users in the database")
    void getAll_ShouldRetrieveAllUsersFromDataBase() {
        List<User> users = userRepository.getAll();
        assertThat(users).isNotNull();
    }

    @Test
    @DisplayName("Get user by ID -- Should return one user when valid user id is provided")
    void getUserById_ShouldReturnUserWhenUserIDIsProvided(){
        Optional<User> newUser = userRepository.getByUserId(user.getId());

        assertThat(newUser.isEmpty()).isFalse();
    }
}
