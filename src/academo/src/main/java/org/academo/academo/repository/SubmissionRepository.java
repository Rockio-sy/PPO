package org.academo.academo.repository;

import org.academo.academo.model.Submission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository {
    void save(Submission submission);
    List<Submission> getAll();
    Optional<Submission> getByTaskId(UUID taskId);

    Optional<UUID> getIdByTaskId(UUID taskId);
}
