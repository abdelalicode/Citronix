package com.youfarm.citronix.domain.entity;

import com.youfarm.citronix.domain.enums.HarvestType;
import com.youfarm.citronix.domain.enums.Season;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Enumerated(EnumType.STRING)
    private HarvestType harvestType;

    @Enumerated(EnumType.STRING)
    private Season season;

    private double totalQuantity;

    private boolean sold = false;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HarvestDetails> harvestDetails;

}

