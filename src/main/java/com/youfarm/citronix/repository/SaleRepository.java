package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
