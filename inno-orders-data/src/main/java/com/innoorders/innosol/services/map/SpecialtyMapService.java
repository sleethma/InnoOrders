package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.ContractorSpecialty;
import com.innoorders.innosol.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class SpecialtyMapService extends AbstractMapService<ContractorSpecialty, Long> implements SpecialtyService {

    @Override
    public Set<ContractorSpecialty> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public ContractorSpecialty save(ContractorSpecialty object) {
        return super.save(object);
    }

    @Override
    public void delete(ContractorSpecialty object) {

    }

    @Override
    public ContractorSpecialty findById(Long id) {
        return super.findById(id);
    }
}
