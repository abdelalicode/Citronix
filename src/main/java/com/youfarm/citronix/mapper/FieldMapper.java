package com.youfarm.citronix.mapper;

import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.dto.field.FieldDTO;
import com.youfarm.citronix.dto.field.FieldResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(source="farm.id" , target="farm_id")
    FieldDTO toDTO(Field f);

    @Mapping(source ="farm.name" , target = "farmName")
    @Mapping(source ="farm.surfaceArea" , target = "farmSurface")
    @Mapping(target = "farmManagerName",  expression = "java(combineManagerNames(field.getFarm().getManager().getFirstName(), field.getFarm().getManager().getLastName()))")
    FieldResponseDTO toResponseDTO(Field field);

    @Mapping(source = "farm_id", target = "farm.id")
    Field toEntity(FieldDTO dto);


    @Named("combineNames")
    default String combineManagerNames(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}
