package com.innoorders.innosol.repos;

import com.innoorders.innosol.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    Customer findByLastName(String lastName);
    List<Customer> findAllByLastNameLike(String lastName);
}
