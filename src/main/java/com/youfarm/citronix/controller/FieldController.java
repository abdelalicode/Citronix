package com.youfarm.citronix.controller;

import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.dto.field.FieldDTO;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.mapper.FieldMapper;
import com.youfarm.citronix.service.FieldService;
import com.youfarm.citronix.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fields")
public class FieldController extends BaseController {

    private final PermissionService permissionService;
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(PermissionService permissionService, FieldService fieldService, FieldMapper fieldMapper) {
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

        fieldService.create(fieldMapper.toEntity(fieldDTO));

        return null;
    }
}
