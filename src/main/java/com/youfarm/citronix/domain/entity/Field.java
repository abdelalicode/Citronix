package com.youfarm.citronix.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double area;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;


}
