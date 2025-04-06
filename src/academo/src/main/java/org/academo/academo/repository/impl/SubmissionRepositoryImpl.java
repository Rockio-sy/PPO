package org.academo.academo.repository.impl;

import org.academo.academo.model.Submission;
import org.academo.academo.repository.SubmissionRepository;
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
public class SubmissionRepositoryImpl implements SubmissionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Submission submission) {
        String sql = "INSERT INTO submission(teacher_id, student_id, answer, task_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                submission.getTeacherId(),
                submission.getStudentId(),
                submission.getAnswer(),
                submission.getTaskId());
    }

    @Override
    public List<Submission> getAll() {
        String sql = "SELECT * FROM submission";
        return jdbcTemplate.query(sql, new SubmissionRowMapper());
    }

    @Override
    public Optional<Submission> getByTaskId(UUID taskId) {
        String sql = "SELECT * FROM submission WHERE task_id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new SubmissionRowMapper(), taskId));
    }

    @Override
    public Optional<UUID> getIdByTaskId(UUID taskId) {
        String sql = "SELECT id FROM submission WHERE task_id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, UUID.class, taskId));
    }

    private class SubmissionRowMapper implements RowMapper<Submission> {
        @Override
        public Submission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Submission submission = new Submission();
            submission.setId((UUID) rs.getObject("id"));
            submission.setStudentId((UUID) rs.getObject("student_id"));
            submission.setTaskId((UUID) rs.getObject("task_id"));
            submission.setTeacherId((UUID) rs.getObject("teacher_id"));
            return submission;
        }
    }
}
