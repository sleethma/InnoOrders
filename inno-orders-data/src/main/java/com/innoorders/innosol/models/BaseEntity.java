package com.innoorders.innosol.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Slf4j
@Data
@MappedSuperclass //establishes master class to JPA
public class BaseEntity implements Serializable {

    //use object Long as to allow null (recommended by Hibernate)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public boolean isNew(){
        return this.id == null;
    }
}
