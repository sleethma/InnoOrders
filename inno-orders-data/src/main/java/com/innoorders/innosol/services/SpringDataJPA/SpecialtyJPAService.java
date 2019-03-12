package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.SalesRepSpecialty;
import com.innoorders.innosol.repos.SalesRepSpecialtyRepo;
import com.innoorders.innosol.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class SpecialtyJPAService implements SpecialtyService {

    SalesRepSpecialtyRepo specialtyRepo;

    public SpecialtyJPAService(SalesRepSpecialtyRepo specialtyRepo) {
        this.specialtyRepo = specialtyRepo;
    }

    @Override
    public Set<SalesRepSpecialty> findAll() {
        Set<SalesRepSpecialty> specialtySet = new HashSet<>();
        specialtyRepo.findAll().forEach(specialtySet :: add);
        return specialtySet;
    }

    @Override
    public SalesRepSpecialty findById(Long id) {
        return specialtyRepo.findById(id).orElse(null);
    }

    @Override
    public SalesRepSpecialty save(SalesRepSpecialty object) {
        return specialtyRepo.save(object);
    }

    @Override
    public void delete(SalesRepSpecialty object) {
    specialtyRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    specialtyRepo.deleteById(id);
    }
}
