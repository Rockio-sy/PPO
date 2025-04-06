package org.academo.academo.service;

import org.academo.academo.dto.SubmissionDTO;

import java.util.UUID;

public interface SubmissionService {
    void submit(SubmissionDTO submission);

    SubmissionDTO getByTaskId(UUID taskId);
}
