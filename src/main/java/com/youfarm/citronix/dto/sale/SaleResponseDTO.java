package com.youfarm.citronix.dto.sale;


import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.dto.ClientReturnDTO;
import com.youfarm.citronix.dto.harvest.HarvestResponseDTO;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDTO {

    private LocalDate saleDate;
    private double unitPrice;
    private double totalPrice;
    private HarvestResponseDTO harvest;
    private ClientReturnDTO client;

}
