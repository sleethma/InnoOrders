package com.innoorders.innosol.models;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_types")
public class ProductType extends BaseEntity {



    @Column(name = "product_type")
    private String name;

    @Override
    public String toString(){
        return name;
    }
}
