package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.common.response.ResponseHandler;
import com.youfarm.citronix.common.utils.SeasonUtility;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.HarvestDetails;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.domain.enums.HarvestType;
import com.youfarm.citronix.domain.enums.Season;
import com.youfarm.citronix.dto.harvest.HarvestDTO;
import com.youfarm.citronix.exception.HarvestTreesSeasonException;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.SurfaceAreaException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FieldRepository;
import com.youfarm.citronix.repository.HarvestDetailsRepository;
import com.youfarm.citronix.repository.HarvestRepository;
import com.youfarm.citronix.repository.TreeRepository;
import com.youfarm.citronix.service.contract.HarvestService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("WholeHarvestService")
public class WholeHarvestService implements HarvestService {

    private final TreeRepository treeRepository;
    private final HarvestRepository harvestRepository;
    private final HarvestDetailsRepository harvestDetailsRepository;
    private final FieldRepository fieldRepository;

    public WholeHarvestService(TreeRepository treeRepository, HarvestRepository harvestRepository, HarvestDetailsRepository harvestDetailsRepository, FieldRepository fieldRepository) {
        this.treeRepository = treeRepository;
        this.harvestRepository = harvestRepository;
        this.harvestDetailsRepository = harvestDetailsRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public boolean beginHarvest(Harvest harvest, Set<Long> ids, Long fieldID, Long authID) {
        Field field = fieldRepository.findById(fieldID).orElseThrow(() -> new NotFoundException("Field not found"));
        Season season = SeasonUtility.getSeasonFromDate(harvest.getHarvestDate());

        if(!field.getFarm().getManager().getId().equals(authID)) {
           throw new UnAuthorizedException("Not authorized to work in this farm");
        }

        int year = harvest.getHarvestDate().getYear();
        harvest.setHarvestType(HarvestType.WHOLE);
        harvest.setSeason(season);
        double totalQuantity = 0.0;

        if(harvest.getHarvestDate().getMonthValue() == 12) {
            Map<Long, LocalDate> recentHarvest = checkRecentHarvest(harvest, field.getTrees());

            if(recentHarvest != null) {
                throw new HarvestTreesSeasonException( recentHarvest, "Can't Harvest this field' trees in same season" );
            }
        }
        else {

            boolean exists = harvestDetailsRepository.existsByFieldSeasonAndYear(fieldID, season, year);
            if (exists) {
                throw new SurfaceAreaException("Can't harvest this field in the same season and year.");
            }
        }
        List<Tree> trees = treeRepository.findByFieldId(fieldID);

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



    public  Map<Long, LocalDate> checkRecentHarvest(Harvest harvest, List<Tree> trees) {
        LocalDate harvestDate = harvest.getHarvestDate();
        LocalDate threeMonthsAgo = harvestDate.minusMonths(3);

        List<Long> treeIds = trees.stream()
                .map(Tree::getId)
                .collect(Collectors.toList());

        List<HarvestDetails> recentHarvestDetails = harvestDetailsRepository.findRecentByTreeIds(
                treeIds, threeMonthsAgo);

        System.out.println(recentHarvestDetails);

        if (!recentHarvestDetails.isEmpty()) {
            return  recentHarvestDetails.stream()
                    .collect(Collectors.toMap(
                            hd -> hd.getTree().getId(),
                            hd -> hd.getHarvest().getHarvestDate()));
        }

        return  null;
    }
}
