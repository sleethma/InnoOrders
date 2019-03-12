package com.innoorders.innosol.services;

import com.innoorders.innosol.models.Customer;

import java.util.List;

public interface CustomerService extends CRUDService<Customer, Long> {
    Customer findByLastName(String lastName);
    List<Customer> findAllByLastNameLike(String lastName);
}
