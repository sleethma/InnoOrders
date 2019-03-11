package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Owner;
import com.innoorders.innosol.repos.OwnerRepo;
import com.innoorders.innosol.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class OwnerDataJPAService implements OwnerService {

    private final OwnerRepo ownerRepo;

    public OwnerDataJPAService(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepo.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerRepo.findAll().forEach(ownerSet :: add);
        return ownerSet;
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepo.findById(id).orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepo.save(object);
    }

    @Override
    public void delete(Owner object) {
    ownerRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    ownerRepo.deleteById(id);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        List<Owner> owners = new ArrayList<>();
        if(lastName.isEmpty()){
            return owners;
        }else{
            owners = ownerRepo.findAllByLastNameLike(lastName);
            return owners;
        }
    }
}
