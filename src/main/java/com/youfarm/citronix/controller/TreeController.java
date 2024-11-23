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
import com.youfarm.citronix.service.implementations.TreeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trees")
public class TreeController extends BaseController {


    private final PermissionService permissionService;
    private final TreeServiceImpl treeService;
    private final TreeMapper treeMapper;

    public TreeController(PermissionService permissionService, TreeServiceImpl treeService, TreeMapper treeMapper) {
        this.permissionService = permissionService;
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    @PostMapping
    ResponseEntity<Object> plantTrees(@Valid @RequestBody PlantingTreesDTO plantingTreesDTO, HttpServletRequest request) {
        Long authId = getUserId(request);


        if(!permissionService.hasPermission(authId, PermissionType.MANAGE_TREES)) {
            throw new UnAuthorizedException("You are not authorized");
        }

        List<Tree> treesCreated = treeService.create(plantingTreesDTO, authId);

        List<TreeVM> treesVm = treesCreated.stream().map(treeMapper::treeToTreeVM).toList();

        return ResponseHandler.responseBuilder("Trees Planted Successfully", HttpStatus.CREATED, treesVm);

    }


    @GetMapping
    ResponseEntity<Object> getTreesByField(@RequestParam Long fieldID , HttpServletRequest request ) {
        Long authID = getUserId(request);

        if(!permissionService.hasPermission(authID, PermissionType.MANAGE_TREES)) {
            throw new UnAuthorizedException("You are not authorized");
        }

        List<Tree> treesByField = treeService.getTreesByFieldId(fieldID, authID);

        List<TreeVM> treesVmByField = treesByField.stream().map(treeMapper::treeToTreeVM).toList();

        if(!treesVmByField.isEmpty()) {
            ResponseHandler.errorBuilder("No Trees in this Field", HttpStatus.NOT_FOUND, "404");
        }

        return ResponseHandler.responseBuilder("Trees Retrieved Successfully", HttpStatus.OK, treesVmByField);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getTreeProductivityPerSeason(@PathVariable Long id, HttpServletRequest request) {
        Long authID = getUserId(request);

        Double treeProductivityPerSeason = treeService.getTreeProductivityPerSeason(id, authID);

        if(treeProductivityPerSeason == 0.0) {
            return ResponseHandler.responseBuilder("No Productivity For this tree", HttpStatus.OK, "0 KG");
        }

        return ResponseHandler.responseBuilder("Tree's Productivity Per Season: (KG) " + treeProductivityPerSeason, HttpStatus.OK, treeProductivityPerSeason + " KG");


    }


}
