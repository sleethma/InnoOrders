package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.SalesRep;
import com.innoorders.innosol.repos.SalesRepRepo;
import com.innoorders.innosol.services.SalesRepService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class SalesRepDataJPAService implements SalesRepService {

    private final SalesRepRepo salesRepRepo;

    public SalesRepDataJPAService(SalesRepRepo salesRepRepo) {
        this.salesRepRepo = salesRepRepo;
    }

    @Override
    public SalesRep findByLastName(String lastName) {
        return salesRepRepo.findByLastName(lastName);
    }

    @Override
    public Set<SalesRep> findAll() {
        Set<SalesRep> salesRepSet = new HashSet<>();
        salesRepRepo.findAll().forEach(salesRepSet::add);
        return salesRepSet;
    }

    @Override
    public SalesRep findById(Long id) {
        return salesRepRepo.findById(id).orElse(null);
    }

    @Override
    public SalesRep save(SalesRep object) {
        return salesRepRepo.save(object);
    }

    @Override
    public void delete(SalesRep object) {
    salesRepRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    salesRepRepo.deleteById(id);
    }
}
