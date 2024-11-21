package com.youfarm.citronix.dto.field;

import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.dto.farm.FarmResponseDTO;
import lombok.*;

@Setter
@Getter
public class FieldResponseDTO {

    private Long id;
    private String name;
    private Double area;
    private String farmName;
    private Double farmSurface;
    private String farmManagerName;

}

