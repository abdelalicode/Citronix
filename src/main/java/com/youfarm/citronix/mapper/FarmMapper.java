package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.dto.farm.FarmDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class)
public interface FarmMapper {

    @Mapping(source = "manager.id", target = "manager_id")
    FarmDTO toDto(Farm farm);

    @Mapping(source = "manager_id", target = "manager.id")
    @Mapping(target = "id", ignore = true)
    Farm toEntity(FarmDTO farmDTO);


    List<FarmDTO> toDtos(List<Farm> farms);
    List<Farm> toEntities(List<FarmDTO> farmDTOs);

}
