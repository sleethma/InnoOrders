package com.innoorders.innosol.repos;

import com.innoorders.innosol.models.Contractor;
import org.springframework.data.repository.CrudRepository;

public interface ContractorRepo extends CrudRepository<Contractor, Long> {
    Contractor findByLastName(String lastName);
}
