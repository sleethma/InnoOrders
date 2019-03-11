package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.RepairRequest;
import com.innoorders.innosol.repos.RepairRequestRepo;
import com.innoorders.innosol.services.RepairRequestService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class RepairRequestJPAService implements RepairRequestService {

    private final RepairRequestRepo repairRequestRepo;

    public RepairRequestJPAService(RepairRequestRepo repairRequestRepo) {
        this.repairRequestRepo = repairRequestRepo;
    }

    @Override
    public Set<RepairRequest> findAll() {
        Set<RepairRequest> requestSet = new HashSet<>();
        repairRequestRepo.findAll().forEach(requestSet::add);
        return requestSet;
    }

    @Override
    public RepairRequest findById(Long id) {
        return repairRequestRepo.findById(id).orElse(null);
    }

    @Override
    public RepairRequest save(RepairRequest repairRequest) {
        return repairRequestRepo.save(repairRequest);
    }

    @Override
    public void delete(RepairRequest object) {
    repairRequestRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    repairRequestRepo.deleteById(id);
    }
}
