package com.innoorders.innosol.services;

import com.innoorders.innosol.models.Contractor;

public interface ContractorService extends CRUDService<Contractor, Long> {

    Contractor findByLastName(String lastName);
}
