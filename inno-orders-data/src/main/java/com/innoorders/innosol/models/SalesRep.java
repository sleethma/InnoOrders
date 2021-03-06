package com.innoorders.innosol.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@Entity
@Table(name = "sales_rep")
public class SalesRep extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
      @JoinTable(name = "sales_rep_specialties",
              joinColumns = @JoinColumn(name = "sales_rep_id"),
              inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    Set<SalesRepSpecialty> specialties = new HashSet<>();
}
