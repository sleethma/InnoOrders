package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.repos.CustomerRepo;
import com.innoorders.innosol.services.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class CustomerDataJPAService implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerDataJPAService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public Customer findByLastName(String lastName) {
        return customerRepo.findByLastName(lastName);
    }

    @Override
    public Set<Customer> findAll() {
        Set<Customer> customerSet = new HashSet<>();
        customerRepo.findAll().forEach(customerSet:: add);
        return customerSet;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepo.findById(id).orElse(null);
    }

    @Override
    public Customer save(Customer object) {
        return customerRepo.save(object);
    }

    @Override
    public void delete(Customer object) {
    customerRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    customerRepo.deleteById(id);
    }

    @Override
    public List<Customer> findAllByLastNameLike(String lastName) {
        List<Customer> customers = new ArrayList<>();
        if(lastName.isEmpty()){
            return customers;
        }else{
            customers = customerRepo.findAllByLastNameLike(lastName);
            return customers;
        }
    }
}
