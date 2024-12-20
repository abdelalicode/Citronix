package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.common.config.FarmConfigProperties;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.SurfaceAreaException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FarmRepository;
import com.youfarm.citronix.repository.FieldRepository;
import com.youfarm.citronix.service.contract.FieldService;
import org.springframework.stereotype.Service;


import java.util.Objects;

@Service
public class FieldServiceImpl implements FieldService {


    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FarmConfigProperties constraints;

    public FieldServiceImpl(FieldRepository fieldRepository, FarmRepository farmRepository, FarmConfigProperties constraints) {
        this.fieldRepository = fieldRepository;
        this.farmRepository = farmRepository;
        this.constraints = constraints;
    }

    public Field create(Field field, Long authId) {

        if(field.getArea() < constraints.getFieldMinAreaHa()) {
            throw new UnAuthorizedException("Field area should at least be more than 0.1 ha");
        }

        Farm farm = farmRepository.findById(field.getFarm().getId()).orElseThrow(() -> new NotFoundException("Farm not found"));

        if(!Objects.equals(farm.getManager().getId(), authId)) {
            throw new UnAuthorizedException("You are not allowed to access this field");
        }

        if(farm.getFields().size() >= constraints.getMaxFieldsInFarm()) {
            throw new SurfaceAreaException("Can't have more than " + constraints.getMaxFieldsInFarm() + " fields");
        }

        field.setFarm(farm);

        if((constraints.getFieldFarmPercentage() * farm.getSurfaceArea() ) < field.getArea() ) {
            throw new SurfaceAreaException("Field surface area should be less than" + constraints.getFieldFarmPercentage() + "% Of the farm");
        }

        Double sum = calculateFieldsTotalArea(field);

        if(farm.getFields().isEmpty() && (field.getArea() >= farm.getSurfaceArea())) {
            throw new SurfaceAreaException("Field Area can't be more than Farm Area");
        }
        else if(sum >= farm.getSurfaceArea()) {
            throw new SurfaceAreaException("All Fields Total Area can't be more than Farm Area");
        }

        return fieldRepository.save(field);
    }

    private Double calculateFieldsTotalArea(Field field) {
        Double farmsfieldsArea = field.getFarm().getFields().stream().mapToDouble(Field::getArea).sum();

        Double newFieldArea = field.getArea();

        return newFieldArea + farmsfieldsArea;

    }

}
