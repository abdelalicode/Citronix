package com.youfarm.citronix.mapper;

import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.dto.field.FieldDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(source="farm.id" , target="farm_id")
    FieldDTO toDTO(Field f);

    @Mapping(source = "farm_id", target = "farm.id")
    Field toEntity(FieldDTO dto);
}
