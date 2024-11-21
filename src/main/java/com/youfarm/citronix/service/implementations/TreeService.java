package com.youfarm.citronix.service.implementations;


import com.youfarm.citronix.common.config.FarmConfigProperties;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.Tree;
import com.youfarm.citronix.dto.tree.PlantingTreesDTO;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.SurfaceAreaException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FieldRepository;
import com.youfarm.citronix.repository.TreeRepository;
import com.youfarm.citronix.service.CitroService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class TreeService{

    private final FieldRepository fieldRepository;
    private final FarmConfigProperties constraints;
    private final TreeRepository treeRepository;

    public TreeService(FieldRepository fieldRepository, FarmConfigProperties constraints, TreeRepository treeRepository) {
        this.fieldRepository = fieldRepository;
        this.constraints = constraints;
        this.treeRepository = treeRepository;
    }

    public List<Tree> create(PlantingTreesDTO plantingTreesDTO, Long authId) {

        System.out.println(plantingTreesDTO);

        Field field = fieldRepository.findById(plantingTreesDTO.fieldId()).orElseThrow( () -> new NotFoundException("Field not found"));

        if(!field.getFarm().getManager().getId().equals(authId)) {
            throw new UnAuthorizedException("Not authorized to plant in this farm");
        }

        Long maxTrees = calculateMaxTreesToFieldArea(field.getArea());

        if(field.getTrees().isEmpty() && maxTrees < plantingTreesDTO.TreesNumber()) {
            throw new SurfaceAreaException("You can't Plant more than " + maxTrees + " trees in this field" );
        }

        if((field.getTrees().size() + plantingTreesDTO.TreesNumber()) > maxTrees) {
            throw new SurfaceAreaException("Field already has " + field.getTrees().size() + " Trees, You can't Plant more than " + maxTrees + " trees in Total" );
        }

        List<Tree> trees = new ArrayList<>();

        IntStream.range(0, Math.toIntExact(plantingTreesDTO.TreesNumber())).forEach(value -> {
            Tree tree = new Tree();
            tree.setField(field);
            tree.setType(plantingTreesDTO.type());
            tree.setPlatingDate(plantingTreesDTO.plantingDate().plusMinutes(value * 5L));
            trees.add(tree);
        });

        System.out.println(trees);

        return treeRepository.saveAll(trees);

    }


    public Long calculateMaxTreesToFieldArea(Double fieldArea) {
        return (long) Math.floor((fieldArea * constraints.getMaxTreesByHectare()));
    }

}
