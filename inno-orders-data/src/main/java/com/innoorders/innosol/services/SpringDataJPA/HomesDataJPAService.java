package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.repos.HomeRepo;
import com.innoorders.innosol.services.HomesService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class HomesDataJPAService implements HomesService {

    HomeRepo homeRepo;

    public HomesDataJPAService(HomeRepo homeRepo) {
        this.homeRepo = homeRepo;
    }

    @Override
    public Set<Home> findAll() {
        Set<Home> homeSet = new HashSet<>();
        homeRepo.findAll().forEach(homeSet :: add);
        return homeSet;
    }

    @Override
    public Home findById(Long id) {
        return homeRepo.findById(id).orElse(null);
    }

    @Override
    public Home save(Home object) {
        return homeRepo.save(object);
    }

    @Override
    public void delete(Home object) {
    homeRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    homeRepo.deleteById(id);
    }
}
