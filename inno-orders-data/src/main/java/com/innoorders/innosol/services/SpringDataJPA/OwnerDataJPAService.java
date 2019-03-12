package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Customer;
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
    public Customer findByLastName(String lastName) {
        return ownerRepo.findByLastName(lastName);
    }

    @Override
    public Set<Customer> findAll() {
        Set<Customer> customerSet = new HashSet<>();
        ownerRepo.findAll().forEach(customerSet:: add);
        return customerSet;
    }

    @Override
    public Customer findById(Long id) {
        return ownerRepo.findById(id).orElse(null);
    }

    @Override
    public Customer save(Customer object) {
        return ownerRepo.save(object);
    }

    @Override
    public void delete(Customer object) {
    ownerRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    ownerRepo.deleteById(id);
    }

    @Override
    public List<Customer> findAllByLastNameLike(String lastName) {
        List<Customer> customers = new ArrayList<>();
        if(lastName.isEmpty()){
            return customers;
        }else{
            customers = ownerRepo.findAllByLastNameLike(lastName);
            return customers;
        }
    }
}
