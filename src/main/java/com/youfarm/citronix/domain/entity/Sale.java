package com.youfarm.citronix.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private double unitPrice;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @PrePersist
    public void calculateTotalPrice() {
        this.totalPrice = this.unitPrice * this.quantity;
    }
}
