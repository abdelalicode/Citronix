package com.youfarm.citronix.mapper;

import com.youfarm.citronix.common.config.GlobalMapperConfig;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.dto.tree.TreeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring", config = GlobalMapperConfig.class)
public interface TreeMapper {


    @Mapping(target = "age", expression = "java(calculateAge(tree.getPlatingDate()))")
    TreeVM treeToTreeVM(Tree tree);


    default Long calculateAge(LocalDateTime platingDate) {
        return platingDate!= null ?ChronoUnit.YEARS.between(platingDate.toLocalDate(), LocalDate.now()) : 0L;
    }

}
