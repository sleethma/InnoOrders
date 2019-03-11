package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.RepairRequest;
import com.innoorders.innosol.services.RepairRequestService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class RepairRequestServiceMap extends AbstractMapService<RepairRequest, Long> implements RepairRequestService {
    @Override
    public Set<RepairRequest> findAll() {
        return super.findAll();
    }

    @Override
    public RepairRequest findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public RepairRequest save(RepairRequest repairRequest) {
        if(repairRequest.getHome() == null)
            throw new RuntimeException("Repair Request Has no Associated Home");
        if(repairRequest.getHome().getId() == null)
            throw new RuntimeException("Invalid Repair Request, No id value");
        return super.save(repairRequest);
    }

    @Override
    public void delete(RepairRequest object) {
    super.deleteByObject(object);
    }
}
