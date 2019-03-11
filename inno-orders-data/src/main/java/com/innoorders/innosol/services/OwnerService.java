package com.innoorders.innosol.services;

import com.innoorders.innosol.models.Owner;

import java.util.List;

public interface OwnerService extends CRUDService<Owner, Long> {
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameLike(String lastName);
}
