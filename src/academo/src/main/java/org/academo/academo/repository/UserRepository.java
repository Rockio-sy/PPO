package org.academo.academo.repository;

import org.academo.academo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void saveUser(User user);

    void removeUser(UUID userId);

    List<User> getAll();

    Optional<User> getByUserId(UUID userId);

    Optional<User> getByUserName(String username);

    Optional<UUID> getIdByUserName(String username);
}
