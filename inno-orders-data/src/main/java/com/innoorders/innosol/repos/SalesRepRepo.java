package com.innoorders.innosol.repos;

import com.innoorders.innosol.models.SalesRep;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepRepo extends CrudRepository<SalesRep, Long> {
    SalesRep findByLastName(String lastName);
}
