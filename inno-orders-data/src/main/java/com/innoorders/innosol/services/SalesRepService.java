package com.innoorders.innosol.services;

import com.innoorders.innosol.models.SalesRep;

public interface SalesRepService extends CRUDService<SalesRep, Long> {

    SalesRep findByLastName(String lastName);
}
