package com.youfarm.citronix.repository;


import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
//    List<Field> findFieldsByFarm(Farm farm);
}
