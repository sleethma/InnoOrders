package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.services.HomesService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Profile({"default", "map"})
public class HomeServiceMap extends AbstractMapService<Home, Long> implements HomesService {
    @Override
    public Set<Home> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Home findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Home save(Home object) {
        return super.save(object);
    }

    @Override
    public void delete(Home object) {
    super.deleteByObject(object);
    }
}
