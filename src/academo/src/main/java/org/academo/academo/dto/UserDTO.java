package org.academo.academo.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserDTO {

    @NotBlank(message = "Username required")
    private String username;
    @NotBlank(message = "Password required")
    private String password;
    private UUID id;
    @NotBlank(message = "Full name required")
    private String fullName;
    @NotBlank(message = "Role is required")
    private String role;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public UserDTO(String username, String password, UUID id, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.fullName = fullName;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
