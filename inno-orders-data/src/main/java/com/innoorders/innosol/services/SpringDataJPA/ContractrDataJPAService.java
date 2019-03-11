package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Contractor;
import com.innoorders.innosol.repos.ContractorRepo;
import com.innoorders.innosol.services.ContractorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class ContractrDataJPAService implements ContractorService {

    private final ContractorRepo contractorRepo;

    public ContractrDataJPAService(ContractorRepo contractorRepo) {
        this.contractorRepo = contractorRepo;
    }

    @Override
    public Contractor findByLastName(String lastName) {
        return contractorRepo.findByLastName(lastName);
    }

    @Override
    public Set<Contractor> findAll() {
        Set<Contractor> contractorSet = new HashSet<>();
        contractorRepo.findAll().forEach(contractorSet::add);
        return contractorSet;
    }

    @Override
    public Contractor findById(Long id) {
        return contractorRepo.findById(id).orElse(null);
    }

    @Override
    public Contractor save(Contractor object) {
        return contractorRepo.save(object);
    }

    @Override
    public void delete(Contractor object) {
    contractorRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    contractorRepo.deleteById(id);
    }
}
