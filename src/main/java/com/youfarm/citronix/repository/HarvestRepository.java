package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {
}
