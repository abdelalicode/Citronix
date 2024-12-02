package com.youfarm.citronix.dto.sale;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SaleDTO(LocalDate saleDate,
                      @Positive
                      double unitPrice,
                      @NotEmpty
                      Long harvest_id,
                      @NotEmpty
                      Long client_id
                      ) {
}
