package com.youfarm.citronix.service.contract;

import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.dto.tree.PlantingTreesDTO;

import java.util.List;

public interface TreeService {

    List<Tree> create(PlantingTreesDTO plantingTreesDTO, Long authId);
    Long calculateMaxTreesToFieldArea(Double fieldArea);
    List<Tree> getTreesByFieldId(Long fieldID, Long authID);
    Double getTreeProductivityPerSeason(Long TreeID, Long authId);
}
