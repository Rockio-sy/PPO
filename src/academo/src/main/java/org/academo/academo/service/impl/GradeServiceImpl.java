package org.academo.academo.service.impl;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.GradeDTO;
import org.academo.academo.model.Grade;
import org.academo.academo.repository.GradeRepository;
import org.academo.academo.service.GradeService;
import org.academo.academo.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private Converter converter;

    @Override
    public void createGrade(GradeDTO grade) {
        try {
            Grade model = converter.DTOtoGrade(grade);
            gradeRepository.save(model);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Grade for this submission already exists", e);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Missing or invalid grade data", e);
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to save grade due to database error", e);
        }
    }

    @Override
    public GradeDTO getBySubmissionId(UUID submissionId) {
        try {
            Optional<Grade> grade = gradeRepository.getBySubmissionId(submissionId);
            return grade.map(converter::gradeToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Grade not found for submission ID: " + submissionId));
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to fetch grade by submission ID", e);
        }
    }
}