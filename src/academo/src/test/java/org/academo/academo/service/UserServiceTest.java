package org.academo.academo.service;

import org.academo.academo.Exception.ResourceNotFoundException;
import org.academo.academo.dto.UserDTO;
import org.academo.academo.extension.TestWatcherExtension;
import org.academo.academo.model.User;
import org.academo.academo.repository.impl.UserRepositoryImpl;
import org.academo.academo.service.impl.UserServiceImpl;
import org.academo.academo.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(TestWatcherExtension.class)
@DisplayName("User Service Tests")
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private Converter converter;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private final UUID userTestId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        user = new User(userId, "12345", "johFull", "student", "rockio");
        userDTO = new UserDTO("rockio", "12345", userTestId, "johnFull", "student");
    }

    @Test
    @DisplayName("Create user -- Success")
    void createUser_Success() {
        when(converter.DTOtoUser(any(UserDTO.class))).thenReturn(user);
        doNothing().when(userRepository).saveUser(any(User.class));

        userService.createUser(userDTO);

        verify(converter, times(1)).DTOtoUser(userDTO);
        verify(userRepository, times(1)).saveUser(user);
    }

    @Test
    @DisplayName("Remove user -- Success")
    void removeUser_Success() {
        doNothing().when(userRepository).removeUser(any());

        userService.removeUser(userDTO.getId());

        verify(userRepository, times(1)).removeUser(userDTO.getId());
    }

    @Test
    @DisplayName("Remove User - Handles ResourceNotFoundException Gracefully")
    void removeUser_DataAccessException_HandledGracefully() {
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).removeUser(userDTO.getId());
        assertThrows(ResourceNotFoundException.class, () -> userService.removeUser(userDTO.getId()));
        verify(userRepository, times(1)).removeUser(userDTO.getId());
    }

    @Test
    @DisplayName("Get All Users - Returns List of Users")
    void getAllUsers_Success() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<UserDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(userDTO);

        when(userRepository.getAll()).thenReturn(users);
        when(converter.userToDTO(any(User.class))).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedDTOs, result);
        verify(userRepository, times(1)).getAll();
        verify(converter, times(1)).userToDTO(user);
    }

    @Test
    @DisplayName("Get All Users - Returns Null When No Users Found")
    void getAllUsers_EmptyResult_ReturnsNull() {
        when(userRepository.getAll()).thenReturn(List.of());

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(result, List.of());
        verify(userRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Get User By ID - Returns User When Found")
    void getByUserId_Success() {
        when(userRepository.getByUserId(userDTO.getId())).thenReturn(Optional.of(user));
        when(converter.userToDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.getByUserId(userDTO.getId());

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository, times(1)).getByUserId(userDTO.getId());
        verify(converter, times(1)).userToDTO(user);
    }

    @Test
    @DisplayName("Get User By ID - Returns Null When User Not Found")
    void getByUserId_NotFound_ThrowsException() {
        when(userRepository.getByUserId(userDTO.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByUserId(userDTO.getId()));


        verify(userRepository, times(1)).getByUserId(userDTO.getId());
    }

    @Test
    @DisplayName("Get User By ID - Returns Null On Database Error")
    void getByUserId_EmptyResultAccessException_ReturnsNull() {
        when(userRepository.getByUserId(userDTO.getId())).thenReturn(Optional.of(user));

        UserDTO result = userService.getByUserId(userDTO.getId());

        assertNull(result);
        verify(userRepository, times(1)).getByUserId(userDTO.getId());
    }
}

