package com.youfarm.citronix.dto.farm;

import com.youfarm.citronix.domain.entity.Field;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FarmResponseDTO {

    private Long id;

    private String name;

    private Double surfaceArea;

    private String managerFirstName;

    private String managerLastName;

    private Long fieldsNumber;


}
