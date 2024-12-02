package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.common.utils.SeasonUtility;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.HarvestDetails;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.domain.enums.HarvestType;
import com.youfarm.citronix.dto.harvest.HarvestDTO;
import com.youfarm.citronix.exception.HarvestTreesSeasonException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FieldRepository;
import com.youfarm.citronix.repository.HarvestDetailsRepository;
import com.youfarm.citronix.repository.HarvestRepository;
import com.youfarm.citronix.repository.TreeRepository;
import com.youfarm.citronix.service.contract.HarvestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("PartialHarvestService")
public class PartialHarvestService implements HarvestService {

    private final TreeRepository treeRepository;
    private final HarvestRepository harvestRepository;
    private final HarvestDetailsRepository harvestDetailsRepository;
    private final FieldRepository fieldRepository;

    public PartialHarvestService(TreeRepository treeRepository, HarvestRepository harvestRepository, HarvestDetailsRepository harvestDetailsRepository, FieldRepository fieldRepository) {
        this.treeRepository = treeRepository;
        this.harvestRepository = harvestRepository;
        this.harvestDetailsRepository = harvestDetailsRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public boolean beginHarvest(Harvest harvest, Set<Long> treeIds, Long fieldID, Long authID) {

        harvest.setHarvestType(HarvestType.PARTIAL);
        harvest.setSeason(SeasonUtility.getSeasonFromDate(harvest.getHarvestDate()));
        double totalQuantity = 0.0;

        List<Long> treesIds = treeIds.stream().toList();

        Map<Long, LocalDate> recentHarvest = checkRecentHarvest(harvest, treesIds);

        if(recentHarvest != null) {
            throw new HarvestTreesSeasonException( recentHarvest, "Can't Harvest this field' trees in same season" );
        }

        List<Tree> trees = treeRepository.findAllById(treeIds);
        Field field = fieldRepository.findFieldByTreeId(trees.get(0).getId());

        if(!field.getFarm().getManager().getId().equals(authID)) {
           throw new UnAuthorizedException("No Authorized To work in this farm");
        }

        Harvest harvestInserted = harvestRepository.save(harvest);

        for (Tree tree : trees) {
            HarvestDetails harvestDetails = new HarvestDetails();
            harvestDetails.setHarvest(harvestInserted);
            harvestDetails.setTree(tree);
            harvestDetails.setQuantity(SeasonUtility.getTreeProductivityPerSeason(tree));
            totalQuantity += harvestDetails.getQuantity();
            harvestDetailsRepository.save(harvestDetails);
        }

        harvest.setTotalQuantity(totalQuantity);
         Harvest harvestCreated= harvestRepository.save(harvest);

         return true;


    }



    public Map<Long, LocalDate> checkRecentHarvest(Harvest harvest, List<Long> treesIds) {
        LocalDate harvestDate = harvest.getHarvestDate();
        LocalDate threeMonthsAgo = harvestDate.minusMonths(3);

        List<HarvestDetails> recentHarvestDetails = harvestDetailsRepository.findRecentByTreeIds(
                treesIds, threeMonthsAgo);

        if (!recentHarvestDetails.isEmpty()) {
            return  recentHarvestDetails.stream()
                    .collect(Collectors.toMap(
                            hd -> hd.getTree().getId(),
                            hd -> hd.getHarvest().getHarvestDate()));
        }

        return  null;
    }

}
