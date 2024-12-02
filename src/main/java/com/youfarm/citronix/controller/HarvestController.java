package com.youfarm.citronix.controller;


import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.dto.harvest.HarvestDTO;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.mapper.FieldMapper;
import com.youfarm.citronix.mapper.HarvestMapper;
import com.youfarm.citronix.service.contract.HarvestService;
import com.youfarm.citronix.service.implementations.PermissionService;
import com.youfarm.citronix.service.implementations.TreeServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/harvest")
public class HarvestController extends BaseController {


    private final PermissionService permissionService;
    private final HarvestService partialHarvestService;
    private final HarvestService wholeHarvestService;
    private final FieldMapper fieldMapper;
    private final HarvestMapper harvestMapper;

    public HarvestController(PermissionService permissionService, @Qualifier("PartialHarvestService") HarvestService partialHarvestService, @Qualifier("WholeHarvestService") HarvestService wholeHarvestService, FieldMapper fieldMapper, HarvestMapper harvestMapper, TreeServiceImpl treeServiceImpl) {
        this.permissionService = permissionService;
        this.partialHarvestService = partialHarvestService;
        this.wholeHarvestService = wholeHarvestService;
        this.fieldMapper = fieldMapper;
        this.harvestMapper = harvestMapper;
    }

    @PostMapping
    ResponseEntity<?> beginHarvest(@RequestBody HarvestDTO harvestDTO, HttpServletRequest request) {
        Long authID = getUserId(request);
        boolean done;

        if(!permissionService.hasPermission(authID, PermissionType.RECORD_HARVEST)) {
            throw new UnAuthorizedException("You do not have permission to record harvest");
        }

        Harvest harvest = harvestMapper.toEntity(harvestDTO);

        if (harvestDTO.harvestTrees() == null || harvestDTO.harvestTrees().isEmpty()) {
            Long fieldID = harvestDTO.fieldId();
            if(wholeHarvestService.beginHarvest(harvest, null, fieldID, authID)) {
                return ResponseHandler.responseBuilder("Field Harvest Collected Successfully", HttpStatus.CREATED, fieldID);
            }
            else return ResponseHandler.errorBuilder("Field Harvest Failed", HttpStatus.BAD_REQUEST, "400");
        } else if(harvestDTO.fieldId() == null) {
            Set<Long> treeIds = harvestDTO.harvestTrees();
            partialHarvestService.beginHarvest(harvest, treeIds, null, authID);
            return ResponseHandler.responseBuilder("Trees Selected Harvest Collected Successfully", HttpStatus.CREATED, treeIds.size());
        }
        else {
            throw new NotFoundException("Provide some Trees to harvest");
        }


    }
}
