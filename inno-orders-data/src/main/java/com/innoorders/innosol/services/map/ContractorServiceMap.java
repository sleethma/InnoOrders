package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Contractor;
import com.innoorders.innosol.models.ContractorSpecialty;
import com.innoorders.innosol.services.ContractorService;
import com.innoorders.innosol.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class ContractorServiceMap extends AbstractMapService<Contractor, Long> implements ContractorService {

    private SpecialtyService specialtyService;

    public ContractorServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Contractor> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    super.deleteById(id);
    }

    @Override
    public void delete(Contractor object) {
    super.deleteByObject(object);
    }

    @Override
    public Contractor findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Contractor save(Contractor object) {
        if(object.getSpecialties().size() > 0){
            object.getSpecialties().forEach(specialty ->{
                if (specialty.getId() == null) {
                    //creates id for specialty if none
                    ContractorSpecialty savedSpecialty = specialtyService.save(specialty);
                    specialty.setId(savedSpecialty.getId());

                }
            });
        }
        return super.save( object);
    }


    @Override
    public Contractor findByLastName(String lastName) {
        return null;
    }
}
