package com.youfarm.citronix.domain.entity;

import com.youfarm.citronix.domain.enums.CitrusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CitrusType type;

    private LocalDateTime platingDate;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;



}
