package org.academo.academo.service;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.SubmissionDTO;
import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.Submission;
import org.academo.academo.repository.SubmissionRepository;
import org.academo.academo.service.impl.SubmissionServiceImpl;
import org.academo.academo.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestWatcherExtension.class})
@DisplayName("Submission Service Implementation Tests")
class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private SubmissionServiceImpl submissionService;

    private SubmissionDTO testSubmissionDTO;
    private Submission testSubmission;
    private final UUID testTaskId = UUID.randomUUID();
    private final UUID studentTestId = UUID.randomUUID();
    private final UUID submissionTestId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        testSubmissionDTO = new SubmissionDTO(submissionTestId, studentTestId, testTaskId, "Solution");
        testSubmission = new Submission(submissionTestId, studentTestId, testTaskId, "Solution");
    }

    @Test
    @DisplayName("Submit - Successfully saves submission")
    void submit_Success() {
        when(converter.DTOtoSubmission(any())).thenReturn(testSubmission);
        doNothing().when(submissionRepository).save(any());

        submissionService.submit(testSubmissionDTO);

        verify(converter).DTOtoSubmission(testSubmissionDTO);
        verify(submissionRepository).save(testSubmission);
    }

    @Test
    @DisplayName("Submit - Throws AlreadyExistsException for duplicate submission")
    void submit_DuplicateKeyException() {
        when(converter.DTOtoSubmission(any())).thenReturn(testSubmission);
        doThrow(DuplicateKeyException.class).when(submissionRepository).save(any());

        assertThrows(AlreadyExistsException.class,
                () -> submissionService.submit(testSubmissionDTO));
    }

    @Test
    @DisplayName("Submit - Throws InvalidDataException for invalid submission data")
    void submit_DataIntegrityViolationException() {
        when(converter.DTOtoSubmission(any())).thenReturn(testSubmission);
        doThrow(DataIntegrityViolationException.class).when(submissionRepository).save(any());

        assertThrows(InvalidDataException.class,
                () -> submissionService.submit(testSubmissionDTO));
    }

    @Test
    @DisplayName("Submit - Throws DatabaseServiceException for general database errors")
    void submit_DataAccessException() {
        when(converter.DTOtoSubmission(any())).thenReturn(testSubmission);
        doThrow(new DataAccessException("DB Error") {
        }).when(submissionRepository).save(any());

        assertThrows(DatabaseServiceException.class,
                () -> submissionService.submit(testSubmissionDTO));
    }

    @Test
    @DisplayName("Get By Task ID - Returns submission when found")
    void getByTaskId_SubmissionFound() {
        when(submissionRepository.getByTaskId(testTaskId)).thenReturn(Optional.of(testSubmission));
        when(converter.submissionToDTO(any())).thenReturn(testSubmissionDTO);

        SubmissionDTO result = submissionService.getByTaskId(testTaskId);

        assertEquals(testSubmissionDTO, result);
        verify(submissionRepository).getByTaskId(testTaskId);
        verify(converter).submissionToDTO(testSubmission);
    }

    @Test
    @DisplayName("Get By Task ID - Throws ResourceNotFoundException when not found")
    void getByTaskId_SubmissionNotFound() {
        when(submissionRepository.getByTaskId(testTaskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> submissionService.getByTaskId(testTaskId));
    }

    @Test
    @DisplayName("Get By Task ID - Throws DatabaseServiceException on database error")
    void getByTaskId_DataAccessException() {
        when(submissionRepository.getByTaskId(testTaskId))
                .thenThrow(new DataAccessException("DB Error") {
                });

        assertThrows(DatabaseServiceException.class,
                () -> submissionService.getByTaskId(testTaskId));
    }
}