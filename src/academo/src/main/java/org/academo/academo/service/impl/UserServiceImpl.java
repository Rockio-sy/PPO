package org.academo.academo.service.impl;

import org.academo.academo.Exception.AlreadyExistsException;
import org.academo.academo.Exception.DatabaseServiceException;
import org.academo.academo.Exception.InvalidDataException;
import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.UserDTO;
import org.academo.academo.model.User;
import org.academo.academo.repository.impl.UserRepositoryImpl;
import org.academo.academo.service.UserService;
import org.academo.academo.util.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private Converter converter;

    @Override
    public void createUser(UserDTO user) {
        User model = converter.DTOtoUser(user);
        try {
            userRepositoryImpl.saveUser(model);
        } catch (DuplicateKeyException e) {
            throw new AlreadyExistsException("Username already exists ", e);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException("Missing or invalid data fields ", e);
        } catch (DataAccessException e) {
            throw new DatabaseServiceException("Failed to save user ", e);
        }
    }

    @Override
    public void removeUser(UUID userId) {
        try {
            userRepositoryImpl.removeUser(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("User not found with ID " + userId, e);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepositoryImpl.getAll();
        List<UserDTO> dto = new ArrayList<>();
        for (User u : users) {
            UserDTO userDTO = converter.userToDTO(u);
            dto.add(userDTO);
        }
        return dto;
    }

    @Override
    public UserDTO getByUserId(UUID userId) {
        try {
            Optional<User> model = userRepositoryImpl.getByUserId(userId);
            if (model.isEmpty()) {
                throw new ResourceNotFoundException("User not found with ID " + userId);
            }
            return converter.userToDTO(model.get());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("User not found with ID " + userId, e);
        }
    }
}
