package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.dto.ClientReturnDTO;
import com.youfarm.citronix.dto.farm.FarmDTO;
import com.youfarm.citronix.dto.farm.FarmResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class)
public interface ClientMapper {

    ClientReturnDTO toClientReturnDTO(User user);

}
