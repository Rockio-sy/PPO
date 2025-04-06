package org.academo.academo.service.impl;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.SubmissionDTO;
import org.academo.academo.model.Submission;
import org.academo.academo.repository.SubmissionRepository;
import org.academo.academo.service.SubmissionService;
import org.academo.academo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private Converter converter;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public void submit(SubmissionDTO submission) {
        try {
            Submission model = converter.DTOtoSubmission(submission);
            submissionRepository.save(model);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Submission for this task already exists", e);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Missing or invalid submission data", e);
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to save submission due to database error", e);
        }
    }

    @Override
    public SubmissionDTO getByTaskId(UUID taskId) {
        try {
            Optional<Submission> submission = submissionRepository.getByTaskId(taskId);
            return submission.map(converter::submissionToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found for task ID: " + taskId));
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch submission by task ID", e);
        }
    }
}