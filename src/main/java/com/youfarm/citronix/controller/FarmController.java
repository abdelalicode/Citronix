package com.youfarm.citronix.controller;

import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.dto.farm.FarmDTO;
import com.youfarm.citronix.exception.*;
import com.youfarm.citronix.mapper.*;
import com.youfarm.citronix.service.PermissionService;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.service.FarmService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/farm")
public class FarmController extends BaseController {


    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private final PermissionService permissionService;
    private final FarmService farmService;
    private final FarmMapper farmMapper;

    public FarmController(PermissionService permissionService, FarmService farmService, FarmMapper farmMapper) {
        this.permissionService = permissionService;
        this.farmService = farmService;
        this.farmMapper = farmMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getFarm(@PathVariable long id, HttpServletRequest request) {
        Farm farmFound = farmService.getFarmById(id).orElseThrow(() -> new NotFoundException("No Farm Found with this id"));
        return ResponseHandler.responseBuilder("Farm Found", HttpStatus.OK, farmMapper.toDto(farmFound));
    }


    @PostMapping
    ResponseEntity<Object> createNewFarm(@Valid @RequestBody FarmDTO farmDto, HttpServletRequest request) {
        Long authId = (Long) request.getAttribute("userId");

        if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            Farm newFarm = farmMapper.toEntity(farmDto);
            Farm farmCreated = farmService.createFarm(newFarm);
            FarmDTO farmDTO = farmMapper.toDto(farmCreated);
            return ResponseHandler.responseBuilder("Farm Created Successfully", HttpStatus.CREATED, farmDTO);
        }
        else {
            throw new UnAuthorizedException("You do not have permission to create a mapper");
        }
    }


    @PutMapping("/{id}/update")
    ResponseEntity<Object> updateFarm(@Valid @RequestBody FarmDTO farmDto, @PathVariable Long id , HttpServletRequest request) {
        Long authId = (Long) request.getAttribute("userId");

        if(permissionService.hasPermission(authId, PermissionType.FULL_ACCESS)) {
            Farm farm = farmMapper.toEntity(farmDto);
            Farm farmUpdated = farmService.updateFarm(farm, id);
            FarmDTO farmDTO = farmMapper.toDto(farmUpdated);
            return ResponseHandler.responseBuilder("Farm Updated Successfully", HttpStatus.OK, farmDTO);
        }
        else {
            throw new UnAuthorizedException("You do not have permission to update a mapper");
        }

    }



    @GetMapping
    ResponseEntity<Object> getFarms(@RequestParam(required = false)
                                    @DateTimeFormat(pattern = DATE_PATTERN) LocalDate fromDate,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(pattern = DATE_PATTERN) LocalDate toDate,
                                    @RequestParam(required = false) String name,
                                    Pageable pageable) {
        List<Farm> farms = farmService.getFarms(fromDate,toDate, name, pageable);

        if(farms.isEmpty()) {
            return ResponseHandler.errorBuilder("No Farm Found", HttpStatus.NOT_FOUND, "404");
        }

        return ResponseHandler.responseBuilder("All Farms", HttpStatus.OK, farms);
    }

}