package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.SalesRep;
import com.innoorders.innosol.models.SalesRepSpecialty;
import com.innoorders.innosol.services.SalesRepService;
import com.innoorders.innosol.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class SalesRepServiceMap extends AbstractMapService<SalesRep, Long> implements SalesRepService {

    private SpecialtyService specialtyService;

    public SalesRepServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<SalesRep> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    super.deleteById(id);
    }

    @Override
    public void delete(SalesRep object) {
    super.deleteByObject(object);
    }

    @Override
    public SalesRep findById(Long id) {
        return super.findById(id);
    }

    @Override
    public SalesRep save(SalesRep object) {
        if(object.getSpecialties().size() > 0){
            object.getSpecialties().forEach(specialty ->{
                if (specialty.getId() == null) {
                    //creates id for specialty if none
                    SalesRepSpecialty savedSpecialty = specialtyService.save(specialty);
                    specialty.setId(savedSpecialty.getId());

                }
            });
        }
        return super.save( object);
    }


    @Override
    public SalesRep findByLastName(String lastName) {
        return null;
    }
}
