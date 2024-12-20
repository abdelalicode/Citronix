package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> , JpaSpecificationExecutor<Farm> {
    Optional<Farm> findByManagerId(Long id);
}
