package org.academo.academo.dto;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class GradeDTO {

    private UUID id;
    @NotNull(message = "Value is required")
    private double value;
    @NotNull(message = "feedback is required")
    private String feedback;
    @NotNull(message = "submission id is required")
    private UUID submissionId;

    // No-args constructor
    public GradeDTO() {
    }

    // Constructor with all arguments
    public GradeDTO(UUID id, double value, String feedback, UUID submissionId) {
        this.id = id;
        this.value = value;
        this.feedback = feedback;
        this.submissionId = submissionId;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    // Constructor without ID
    public GradeDTO(double value, String feedback, UUID submissionId) {
        this.value = value;
        this.feedback = feedback;
        this.submissionId = submissionId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // Getters and setters (omitted for brevity)
}

