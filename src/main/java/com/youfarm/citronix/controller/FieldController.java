package com.youfarm.citronix.controller;

import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.dto.field.FieldDTO;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.mapper.FieldMapper;
import com.youfarm.citronix.service.implementations.FieldServiceImpl;
import com.youfarm.citronix.service.implementations.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fields")
public class FieldController extends BaseController {

    private final PermissionService permissionService;
    private final FieldServiceImpl fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(PermissionService permissionService, FieldServiceImpl fieldService, FieldMapper fieldMapper) {
        this.permissionService = permissionService;
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @PostMapping
    ResponseEntity<Object> createField(@RequestBody FieldDTO fieldDTO, HttpServletRequest request) {
        Long authID = getUserId(request);

        if(!permissionService.hasPermission(authID, PermissionType.MANAGE_FIELDS)) {
           throw new UnAuthorizedException("No permission granted");
        }

        Field field = fieldService.create(fieldMapper.toEntity(fieldDTO) , authID);

        return ResponseHandler.responseBuilder("Field Created successfully", HttpStatus.CREATED, fieldMapper.toResponseDTO(field));
    }
}
