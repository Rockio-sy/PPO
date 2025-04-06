package org.academo.academo.repository.impl;

import org.academo.academo.model.Task;
import org.academo.academo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Task task) {
        String sql = "INSERT INTO task(title, description, student_id, teacher_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getStudentId(), task.getTeacherId());
    }


    @Override
    public Optional<UUID> getIdByTaskTitle(String title) {
        String sql = "SELECT id FROM task WHERE title = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, UUID.class, title));
    }

    @Override
    public List<Task> getAll() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }


    @Override
    public Optional<Task> getById(UUID taskId) {
        String sql = "SELECT * FROM task WHERE id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskId));
    }

    @Override
    public List<Task> getAllByStudentId(UUID studentId) {
        String sql = "SELECT * FROM task WHERE student_id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), studentId);
    }

    @Override
    public List<Task> getAllByTeacherId(UUID teacherId) {
        String sql = "SELECT * FROM task WHERE teacher_id = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), teacherId);
    }


    private class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId((UUID) rs.getObject("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setStudentId((UUID) rs.getObject("student_id"));
            task.setTeacherId((UUID) rs.getObject("teacher_id"));
            return task;
        }
    }
}
