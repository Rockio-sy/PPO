package org.academo.academo.model;

import java.util.UUID;

public class Grade {
    private UUID id;
    private double value;
    private String feedback;
    private UUID submissionId;

    public Grade() {
    }


    public Grade(UUID id, double value, String feedback, UUID submissionId) {
        this.id = id;
        this.value = value;
        this.feedback = feedback;
        this.submissionId = submissionId;
    }

    public Grade(double value, String feedback, UUID submissionId) {
        
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
}

