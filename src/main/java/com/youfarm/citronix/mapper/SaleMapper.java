package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Sale;
import com.youfarm.citronix.dto.sale.SaleDTO;
import com.youfarm.citronix.dto.sale.SaleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class, uses = {HarvestMapper.class, ClientMapper.class})
public interface SaleMapper {


    @Mapping(source = "harvest_id", target = "harvest.id")
    @Mapping(source = "client_id", target = "client.id")
    @Mapping(target = "id", ignore = true)
    Sale toEntity(SaleDTO saleDTO);

    @Mapping(source = "harvest", target = "harvest")
    @Mapping(source = "client", target = "client")
    SaleResponseDTO entityToDto(Sale sale);



}
