package org.academo.academo.repository;

import org.academo.academo.model.Grade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepository {
    void save(Grade grade);
    Optional<Grade> getById(UUID id);
    List<Grade> getAll();

    Optional<Grade> getBySubmissionId(UUID submissionId);
}
