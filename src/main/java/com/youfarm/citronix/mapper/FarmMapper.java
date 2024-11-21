package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.dto.farm.FarmDTO;
import com.youfarm.citronix.dto.farm.FarmResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class)
public interface FarmMapper {

    @Mapping(source = "manager.id", target = "manager_id")
    FarmDTO toDto(Farm farm);

    @Mapping(source = "manager_id", target = "manager.id")
    @Mapping(target = "id", ignore = true)
    Farm toEntity(FarmDTO farmDTO);


    @Mapping(source= "manager.firstName" , target = "managerFirstName")
    @Mapping(source= "manager.lastName" , target = "managerLastName")
    @Mapping(source= "fields" , target = "fieldsNumber" , qualifiedByName = "fieldsSize")
    FarmResponseDTO entityToDto(Farm farm);


    List<FarmResponseDTO> toDtos(List<Farm> farms);



    List<Farm> toEntities(List<FarmDTO> farmDTOs);


    @Named("fieldsSize")
    default Long fieldsSize(List<Field> fields) {
        return fields != null ? (long) fields.size() : 0L;
    }

}
