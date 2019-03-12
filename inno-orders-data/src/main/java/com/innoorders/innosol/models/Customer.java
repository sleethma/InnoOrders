package com.innoorders.innosol.models;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@EqualsAndHashCode(exclude = {"orders"})
@Entity
@Table(name = "customers")
public class Customer extends Person  {

    @Column(name = "address")
    private String address;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "city")
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();


    public Order getOrder(String firstName, String lastName, boolean ignoreNew) {
        firstName = firstName.toLowerCase();
        lastName = lastName.toLowerCase();
        for (Order order : orders) {
            if (!ignoreNew || !order.isNew()) {
                String compFirstName = order.getResidentFirstName().toLowerCase();
                String compLastName = order.getResidentLastName().toLowerCase();
                if (compFirstName.equals(firstName) && compLastName.equals(lastName)) {
                    return order;
                }
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
