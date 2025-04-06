package org.academo.academo.service;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.GradeDTO;
import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.Grade;
import org.academo.academo.repository.GradeRepository;
import org.academo.academo.service.impl.GradeServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, TestWatcherExtension.class})
@DisplayName("Grade Service Implementation Tests")
class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private GradeServiceImpl gradeService;

    private GradeDTO testGradeDTO;
    private Grade testGrade;
    private final UUID submissionTestId = UUID.randomUUID();
    private final UUID gradeTestId = UUID.randomUUID();


    @BeforeEach
    void setUp() {
        testGradeDTO = new GradeDTO(gradeTestId, 90, "Good work", submissionTestId);
        testGrade = new Grade(gradeTestId, 90, "Good work", submissionTestId);
    }

    @Test
    @DisplayName("Create Grade - Success")
    void createGrade_Success() {
        when(converter.DTOtoGrade(any())).thenReturn(testGrade);
        doNothing().when(gradeRepository).save(any());

        gradeService.createGrade(testGradeDTO);

        verify(converter).DTOtoGrade(testGradeDTO);
        verify(gradeRepository).save(testGrade);
    }

    @Test
    @DisplayName("Create Grade - Throws AlreadyExistsException for duplicate grade")
    void createGrade_DuplicateKeyException() {
        when(converter.DTOtoGrade(any())).thenReturn(testGrade);
        doThrow(DuplicateKeyException.class).when(gradeRepository).save(any());

        assertThrows(AlreadyExistsException.class,
                () -> gradeService.createGrade(testGradeDTO));
    }

    @Test
    @DisplayName("Create Grade - Throws InvalidDataException for invalid grade data")
    void createGrade_DataIntegrityViolationException() {
        when(converter.DTOtoGrade(any())).thenReturn(testGrade);
        doThrow(DataIntegrityViolationException.class).when(gradeRepository).save(any());

        assertThrows(InvalidDataException.class,
                () -> gradeService.createGrade(testGradeDTO));
    }

    @Test
    @DisplayName("Create Grade - Throws DatabaseServiceException for database errors")
    void createGrade_DataAccessException() {
        when(converter.DTOtoGrade(any())).thenReturn(testGrade);
        doThrow(new DataAccessException("DB Error") {}).when(gradeRepository).save(any());

        assertThrows(DatabaseServiceException.class,
                () -> gradeService.createGrade(testGradeDTO));
    }

    @Test
    @DisplayName("Get By Submission ID - Returns grade when found")
    void getBySubmissionId_GradeFound() {
        UUID submissionTestId = UUID.randomUUID();
        when(gradeRepository.getBySubmissionId(any(UUID.class))).thenReturn(Optional.of(testGrade));
        when(converter.gradeToDTO(any())).thenReturn(testGradeDTO);

        GradeDTO result = gradeService.getBySubmissionId(submissionTestId);

        assertEquals(testGradeDTO, result);
        verify(gradeRepository).getBySubmissionId(submissionTestId);
        verify(converter).gradeToDTO(testGrade);
    }

    @Test
    @DisplayName("Get By Submission ID - Throws ResourceNotFoundException when not found")
    void getBySubmissionId_GradeNotFound() {
        UUID submissionTestId = UUID.randomUUID();
        when(gradeRepository.getBySubmissionId(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> gradeService.getBySubmissionId(submissionTestId));
    }

    @Test
    @DisplayName("Get By Submission ID - Throws DatabaseServiceException on database error")
    void getBySubmissionId_DataAccessException() {
        UUID submissionTestId = UUID.randomUUID();
        when(gradeRepository.getBySubmissionId(any(UUID.class)))
                .thenThrow(new DataAccessException("DB Error") {});

        assertThrows(DatabaseServiceException.class,
                () -> gradeService.getBySubmissionId(submissionTestId));
    }
}