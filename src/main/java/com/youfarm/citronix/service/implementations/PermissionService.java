package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private UserRepository userRepository;

    public PermissionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean hasPermission(Long userId, PermissionType permission) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null || user.getRole() == null){
            return false;
        }


        return user.getRole().getPermissions().stream()
                .anyMatch(p -> p.getName().equals(permission));

    }
}
