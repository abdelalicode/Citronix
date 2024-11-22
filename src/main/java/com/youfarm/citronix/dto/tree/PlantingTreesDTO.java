package com.youfarm.citronix.dto.tree;

import com.youfarm.citronix.common.utils.ValidPlantingMonths;
import com.youfarm.citronix.domain.enums.CitrusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PlantingTreesDTO(@Positive Long TreesNumber,
                               CitrusType type,
                               @Past(message = "Can't record plantation in the future")
                               @ValidPlantingMonths
                               LocalDateTime plantingDate,
                               Long fieldId) {
}
