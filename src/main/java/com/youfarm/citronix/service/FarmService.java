package com.youfarm.citronix.service;

import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FarmRepository;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FarmService {


    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    public FarmService(FarmRepository farmRepository, UserRepository userRepository) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Farm createFarm(Farm farm){

        Optional<Farm> existingFarm = farmRepository.findByManagerId(farm.getManager().getId());
        User manager  = userRepository.findById(farm.getManager().getId()).orElseThrow( () -> new NotFoundException("Manager with id: "+ farm.getManager().getId() + " Not Found"));

        if(existingFarm.isPresent()){
            throw new UnAuthorizedException("Manager already have a farm to manage");
        }
        if(!manager.getRole().getName().equals("FARM_MANAGER")){
            throw new UnAuthorizedException("The User is not a Farm Manager");
        }
        farm.setManager(manager);

        return farmRepository.save(farm);
    }

    public Farm updateFarm(Farm farm, Long id){
        Farm existingFarm = farmRepository.findById(id).orElseThrow(() -> new NotFoundException("No Farm found by this id"));
        System.out.println(farm);
        if(farm.getManager().getId() != null){
            existingFarm.setManager(farm.getManager());
        }
        if(farm.getSurfaceArea() != null){
            existingFarm.setSurfaceArea(farm.getSurfaceArea());
        }
        if(farm.getName() != null){
            existingFarm.setName(farm.getName());
        }

        return farmRepository.save(existingFarm);

    }

    public Optional<Farm> getFarmById(Long id){
        return farmRepository.findById(id);
    }

    public List<Farm> getFarms(LocalDate fromDate, LocalDate toDate, String name, Pageable pageable) {


        List<Farm> farms = farmRepository.findAll(new Specification<Farm>() {
            @Override
            public Predicate toPredicate(Root<Farm> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();

                if(Objects.nonNull(fromDate) && Objects.nonNull(toDate) && fromDate.isBefore(toDate)){
                    p = cb.and(p, cb.between(root.get("creationDate"), fromDate, toDate));
                }

                if(!StringUtils.isEmpty(name)) {
                    p = cb.and(p, cb.like(root.get("name"), "%"+name+"%"));
                }

                cq.orderBy(cb.desc(root.get("name")), cb.asc(root.get("id")));
                return p;
            }
        }, pageable).getContent();

        return farms;
    }
}
