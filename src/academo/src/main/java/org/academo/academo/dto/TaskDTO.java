package org.academo.academo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TaskDTO {
    private UUID id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "student id is required")
    private UUID studentId;
    @NotNull(message = "Teacher id is required")
    private UUID teacherId;

    // No-args constructor
    public TaskDTO() {
    }

    // Constructor with all arguments
    public TaskDTO(UUID id, String title, String description, UUID studentId, UUID teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + studentId +
                ", teacherId=" + teacherId +
                '}';
    }

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
        this.teacherId = teacherId;
    }

    // Constructor without ID
    public TaskDTO(String title, String description, UUID studentId, UUID teacherId) {
        this.title = title;
        this.description = description;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
