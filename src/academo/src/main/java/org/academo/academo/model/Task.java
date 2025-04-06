package org.academo.academo.model;

import java.util.UUID;

// Task Model Class
public class Task {
    private UUID id;
    private String title;
    private String description;
    private UUID studentId;
    private UUID teacherId;

    // No-args constructor
    public Task() {
    }

    public Task(UUID id, String title, String description, UUID studentId, UUID teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public Task(String title, String description, UUID studentId, UUID teacherId) {
        
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

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(UUID teacherId) {
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", studentId=" + studentId +
                ", teacherId=" + teacherId +
                '}';
    }
}
