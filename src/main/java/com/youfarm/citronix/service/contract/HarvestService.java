package com.youfarm.citronix.service.contract;

import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.dto.harvest.HarvestDTO;

import java.util.Set;

public interface HarvestService {

    boolean beginHarvest(Harvest harvest, Set<Long> ids, Long fieldId, Long authID);
}
