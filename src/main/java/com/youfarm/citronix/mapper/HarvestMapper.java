package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.HarvestDetails;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.dto.harvest.HarvestDTO;
import com.youfarm.citronix.dto.harvest.HarvestResponseDTO;
import com.youfarm.citronix.dto.tree.TreeVM;
import com.youfarm.citronix.dto.tree.TreesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class)
public interface HarvestMapper {


    Harvest toEntity(HarvestDTO harvestDTO);

    HarvestResponseDTO toHarvestDTO(Harvest harvest);


}
