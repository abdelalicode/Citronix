package com.youfarm.citronix.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youfarm.citronix.domain.entity.Permission;
import com.youfarm.citronix.repository.PermissionRepository;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.domain.entity.Role;
import com.youfarm.citronix.repository.RoleRepository;
import com.youfarm.citronix.domain.enums.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final ObjectMapper objectMapper;

    public DataLoader(RoleRepository roleRepository, PermissionRepository permissionRepository, ObjectMapper objectMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

            if(roleRepository.count() == 0 ) {
                for (PermissionType permissionType : PermissionType.values()) {
                    Permission permission = new Permission();
                    permission.setName(permissionType);
                    permissionRepository.save(permission);
                }
                Role adminRole = new Role();
                adminRole.setName(String.valueOf(RoleType.ADMIN));
                adminRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.FULL_ACCESS)
                ));
                roleRepository.save(adminRole);

                Role employeeRole = new Role();
                employeeRole.setName(String.valueOf(RoleType.FARM_MANAGER));
                employeeRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.MANAGE_FIELDS),
                        permissionRepository.findByName(PermissionType.MANAGE_TREES),
                        permissionRepository.findByName(PermissionType.RECORD_HARVEST),
                        permissionRepository.findByName(PermissionType.VIEW_HARVEST_DATA),
                        permissionRepository.findByName(PermissionType.MANAGE_SALES)
                ));
                roleRepository.save(employeeRole);

                Role clientRole = new Role();
                clientRole.setName(String.valueOf(RoleType.SALES_MANAGER));
                clientRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.VIEW_HARVEST_DATA),
                        permissionRepository.findByName(PermissionType.MANAGE_SALES)
                ));
                roleRepository.save(clientRole);
            }
            else {
                log.info("Found {} roles", roleRepository.count());
            }
    }
}
