package com.youfarm.citronix.controller;


import com.youfarm.citronix.common.BaseController;
import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.domain.entity.Sale;
import com.youfarm.citronix.domain.enums.PermissionType;
import com.youfarm.citronix.dto.sale.SaleDTO;
import com.youfarm.citronix.dto.sale.SaleResponseDTO;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.mapper.SaleMapper;
import com.youfarm.citronix.service.implementations.PermissionService;
import com.youfarm.citronix.service.implementations.SaleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SaleController extends BaseController {

    private final PermissionService permissionService;
    private final SaleService saleService;
    private final SaleMapper saleMapper;

    public SaleController(PermissionService permissionService, SaleService saleService, SaleMapper saleMapper) {
        this.permissionService = permissionService;
        this.saleService = saleService;
        this.saleMapper = saleMapper;
    }

    @PostMapping
    ResponseEntity<Object> makeSale(@RequestBody SaleDTO saleDTO, HttpServletRequest request) {
        Long authId = getUserId(request);

        if(!permissionService.hasPermission(authId, PermissionType.MANAGE_SALES)) {
            throw new UnAuthorizedException("You do not have permission to make sales");
        }

        Sale sale = saleMapper.toEntity(saleDTO);

        Sale saleDone = saleService.create(sale);

        SaleResponseDTO sdto = saleMapper.entityToDto(saleDone);


        return ResponseHandler.responseBuilder("Harvest " + saleDone.getHarvest().getId() + "Sold Successfully", HttpStatus.CREATED, sdto);
    }

}
