package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByname(String name);
}
