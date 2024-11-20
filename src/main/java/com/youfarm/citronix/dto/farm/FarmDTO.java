package com.youfarm.citronix.dto.farm;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record FarmDTO(String name,
                      String localisation,

                      @NotNull(message = "Surface area is required")
                      @Min(value = 0, message = "Surface area must be positive")
                      Double surfaceArea,

                      @NotNull(message = "Creation date is required")
                      @Past(message = "Creation date must be in the past")
                      LocalDate creationDate,
                      Long manager_id) {
}
