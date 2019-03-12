package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.SalesRepSpecialty;
import com.innoorders.innosol.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class SpecialtyMapService extends AbstractMapService<SalesRepSpecialty, Long> implements SpecialtyService {

    @Override
    public Set<SalesRepSpecialty> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public SalesRepSpecialty save(SalesRepSpecialty object) {
        return super.save(object);
    }

    @Override
    public void delete(SalesRepSpecialty object) {

    }

    @Override
    public SalesRepSpecialty findById(Long id) {
        return super.findById(id);
    }
}
