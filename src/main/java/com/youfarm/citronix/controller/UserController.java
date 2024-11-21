package com.youfarm.citronix.controller;

import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.service.implementations.PermissionService;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.common.utils.EntityDtoMapper;
import com.youfarm.citronix.common.utils.TokenUtil;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.dto.UserDTO;
import com.youfarm.citronix.dto.UserReturnDTO;
import com.youfarm.citronix.service.implementations.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EntityDtoMapper mapper;
    private final TokenUtil tokenUtil;
    private final PermissionService permissionService;


    public UserController(UserService userService, EntityDtoMapper mapper, TokenUtil tokenUtil, PermissionService permissionService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenUtil = tokenUtil;
        this.permissionService = permissionService;
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable long id, HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long authId = (Long) request.getAttribute("userId");


        if( authId == id || permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            User user = mapper.mapToEntity(userDTO, User.class);

            User updatedUser = userService.updateUser(user, id);
            UserReturnDTO createdUserDTO = mapper.mapToDto(updatedUser, UserReturnDTO.class);
            return ResponseHandler.responseBuilder("Updated successfully", HttpStatus.CREATED, createdUserDTO);
        }
        else {
            throw new UnAuthorizedException("You are not allowed");
        }



    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long authId = (Long) request.getAttribute("userId");

        if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {

            Optional<List<User>> allUsersOptional = userService.findAllUsers();
            if(allUsersOptional.isEmpty()) {
                return ResponseHandler.responseBuilder("All Users List", HttpStatus.OK, "No users found");
            }
            else  {
                List<User> allUsers = allUsersOptional.get();
                List<UserReturnDTO> userDTOs = mapper.mapToEntityList(allUsers, UserReturnDTO.class);
                return ResponseHandler.responseBuilder("All Users List", HttpStatus.OK, userDTOs);
            }
        }
        else {
            throw new UnAuthorizedException("You are not allowed");
        }
    }


}