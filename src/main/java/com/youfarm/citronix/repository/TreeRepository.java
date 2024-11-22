package com.youfarm.citronix.repository;

import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByFieldId(Long fieldId);
}
