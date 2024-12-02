package com.youfarm.citronix.dto.harvest;

import java.time.LocalDate;
import java.util.Set;

public record HarvestDTO(
        Long fieldId,
        LocalDate harvestDate,
        Set<Long> harvestTrees
) {
}
