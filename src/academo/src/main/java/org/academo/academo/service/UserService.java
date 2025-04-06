package org.academo.academo.service;

import org.academo.academo.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void createUser(UserDTO user);

    void removeUser(UUID userId);

    List<UserDTO> getAllUsers();

    UserDTO getByUserId(UUID userId);
}
