package com.youfarm.citronix.dto.tree;

import com.youfarm.citronix.domain.enums.CitrusType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@Builder
public class TreeVM {

    private int id;
    private CitrusType type;
    private LocalDateTime platingDate;
    private Long age;


    public Long calculateAge() {
        if (platingDate == null) {
            return 0L;
        }
        return ChronoUnit.YEARS.between(platingDate.toLocalDate(), LocalDate.now());
    }



}
