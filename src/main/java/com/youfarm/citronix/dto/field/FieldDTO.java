package com.youfarm.citronix.dto.field;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record FieldDTO(@NotEmpty String name,
                       @Positive(message = "Area must be grater than 0") Double area,
                       Long farm_id) {
}
