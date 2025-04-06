package org.academo.academo.service;

import org.academo.academo.dto.GradeDTO;

import java.util.UUID;

public interface GradeService {
    void createGrade(GradeDTO grade);

    GradeDTO getBySubmissionId(UUID submissionId);
}
