package com.youfarm.citronix.service.contract;

import com.youfarm.citronix.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    Optional<User> validateUser(String email, String password);
    User updateUser(User user, Long userId);
    Optional<List<User>> findAllUsers();
}
