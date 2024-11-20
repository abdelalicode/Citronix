package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Permission;
import com.youfarm.citronix.domain.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(PermissionType name);

    Permission findByName(PermissionType permissionType);
}
