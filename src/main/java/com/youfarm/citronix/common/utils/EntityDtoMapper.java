package com.youfarm.citronix.common.utils;

import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.dto.UserReturnDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class EntityDtoMapper {
    private final ModelMapper modelMapper;

    public EntityDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.typeMap(User.class, UserReturnDTO.class).addMappings(mapper -> {
                mapper.map(src -> src.getRole().getName(), UserReturnDTO::setRoleName);
                }
        );


    }

    public <D, T> D mapToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <D, T> T mapToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <D, T> List<D> mapToDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> mapToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <D, T> List<T> mapToEntityList(List<D> dtoList, Class<T> entityClass) {
        return dtoList.stream()
                .map(dto -> mapToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }
}