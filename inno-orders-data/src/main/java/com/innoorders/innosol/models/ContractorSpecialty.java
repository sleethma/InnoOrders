package com.innoorders.innosol.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Slf4j
@Data
@Entity
@Table(name = "specialties")
public class ContractorSpecialty extends BaseEntity {

    @Column(name = "description")
    private String specialty;
}
