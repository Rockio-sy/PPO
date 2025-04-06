package org.academo.academo.model;

import java.util.UUID;

public class Submission {
    private UUID id;
    private UUID teacherId;
    private UUID studentId;
    private UUID taskId;
    private String answer;

    // No-args constructor
    public Submission() {
    }

    public Submission(UUID id, UUID teacherId, UUID studentId, UUID taskId, String answer) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.taskId = taskId;
        this.answer = answer;
    }
    

    public Submission( UUID teacherId, UUID studentId, UUID taskId, String answer) {
        
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

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", studentId=" + studentId +
                ", taskId=" + taskId +
                ", answer='" + answer + '\'' +
                '}';
    }
}

