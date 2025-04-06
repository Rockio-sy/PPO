package org.academo.academo.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

// Submission DTO Class
public class SubmissionDTO {
    private UUID id;
    @NotNull(message = "Teacher id is required")
    private UUID teacherId;
    @NotNull(message = "Student id is required")
    private UUID studentId;
    @NotNull(message = "Task id is required")
    private UUID taskId;
    @NotNull(message = "Answer is required")
    private String answer;

    public SubmissionDTO() {
    }

    // Constructor with all arguments
    public SubmissionDTO(UUID id, UUID teacherId, UUID studentId, UUID taskId, String answer) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.taskId = taskId;
        this.answer = answer;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Constructor without ID
    public SubmissionDTO(UUID teacherId, UUID studentId, UUID taskId, String answer) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.taskId = taskId;
        this.answer = answer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

}


