package com.youfarm.citronix.dto.harvest;

import com.youfarm.citronix.domain.enums.HarvestType;
import com.youfarm.citronix.domain.enums.Season;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Setter
@Getter
public class HarvestResponseDTO {

    private Long id;
    private LocalDate harvestDate;
    private HarvestType harvestType;
    private Season season;
    private double totalQuantity;
    private boolean sold;
}
