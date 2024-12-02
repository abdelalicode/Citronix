package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.domain.entity.Harvest;
import com.youfarm.citronix.domain.entity.Sale;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.exception.NotFoundException;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.HarvestRepository;
import com.youfarm.citronix.repository.SaleRepository;
import com.youfarm.citronix.repository.UserRepository;
import com.youfarm.citronix.service.CitroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService implements CitroService<Sale,Long> {
    private final UserRepository userRepository;
    private final HarvestRepository harvestRepository;
    private final SaleRepository saleRepository;

    public SaleService(UserRepository userRepository, HarvestRepository harvestRepository, SaleRepository saleRepository) {
        this.userRepository = userRepository;
        this.harvestRepository = harvestRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public Sale create(Sale sale) {

        User client = userRepository.findById(sale.getClient().getId()).orElseThrow(()-> new NotFoundException("Client not found"));
        Harvest harvest = harvestRepository.findById(sale.getHarvest().getId()).orElseThrow(()-> new NotFoundException("Harvest not found"));
        if(harvest.isSold()) {
            throw new UnAuthorizedException("The Harvest already been sold");
        }
        sale.setClient(client);
        sale.setHarvest(harvest);
        sale.setQuantity(harvest.getTotalQuantity());
        harvest.setSold(true);
        harvestRepository.save(harvest);
        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAll() {
        return List.of();
    }

    @Override
    public Optional<Sale> getById(Long aLong) {
        return Optional.empty();
    }
}
