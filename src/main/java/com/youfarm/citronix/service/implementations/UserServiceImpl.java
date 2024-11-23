package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.repository.UserRepository;
import com.youfarm.citronix.domain.entity.Role;
import com.youfarm.citronix.repository.RoleRepository;
import com.youfarm.citronix.domain.enums.RoleType;
import com.youfarm.citronix.service.contract.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User createUser(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(null);

        Role defaultRole = roleRepository.findByname(String.valueOf(RoleType.FARM_MANAGER));
        user.setRole(defaultRole);


        return userRepository.save(user);

    }

    public Optional<User> validateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public User updateUser(User user, Long userId) {

        Optional<User> existingUser = userRepository.findById(userId);

        if(existingUser.isEmpty()) {
            throw new NotFoundException("User not found.");
        }

        existingUser.get().setFirstName(user.getFirstName());
        existingUser.get().setLastName(user.getLastName());
        existingUser.get().setEmail(user.getEmail());
        existingUser.get().setAge(user.getAge());
        return userRepository.save(existingUser.get());

    }

    public Optional<List<User>> findAllUsers() {
        return Optional.of(userRepository.findAll());
    }


}
