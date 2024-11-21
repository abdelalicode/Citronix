package com.youfarm.citronix.controller;

import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.dto.tree.PlantingTreesDTO;
import com.youfarm.citronix.dto.tree.TreeVM;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.mapper.TreeMapper;
import com.youfarm.citronix.service.implementations.PermissionService;
import com.youfarm.citronix.service.implementations.TreeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trees")
public class TreeController extends BaseController {


    private final PermissionService permissionService;
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(PermissionService permissionService, TreeService treeService, TreeMapper treeMapper) {
        this.permissionService = permissionService;
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    @PostMapping
    ResponseEntity<Object> plantTrees(@RequestBody PlantingTreesDTO plantingTreesDTO, HttpServletRequest request) {
        Long authId = getUserId(request);


        if(!permissionService.hasPermission(authId, PermissionType.MANAGE_TREES)) {
            throw new UnAuthorizedException("You are not authorized");
        }

        List<Tree> treesCreated = treeService.create(plantingTreesDTO, authId);

        List<TreeVM> treesVm = treesCreated.stream().map(treeMapper::treeToTreeVM).toList();

        return ResponseHandler.responseBuilder("Trees Planted Successfully", HttpStatus.CREATED, treesVm);

    }
}
