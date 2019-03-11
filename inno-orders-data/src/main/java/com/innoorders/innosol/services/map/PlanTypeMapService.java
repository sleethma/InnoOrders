package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.PlanTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class PlanTypeMapService extends AbstractMapService<PlanType, Long> implements PlanTypeService {
    @Override
    public Set<PlanType> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    super.deleteById(id);
    }

    @Override
    public void delete(PlanType object) {
    super.deleteByObject(object);
    }

    @Override
    public PlanType save(PlanType object) {
        return super.save(object);
    }

    @Override
    public PlanType findById(Long id) {
        return super.findById(id);
    }
}
