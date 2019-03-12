package com.innoorders.innosol.models;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType productType;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "resident_first_name")
    private String residentFirstName;

    @Column(name = "resident_last_name")
    private String residentLastName;

    @Column(name = "property_address")
    private String propertyAddress;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<RepairRequest> repairs = new HashSet<>();

    public void addRepairRequest(RepairRequest repairRequest){
        repairs.add(repairRequest);
    }

    //todo: not overriding hash causes SO Exception between order and repair oneToMany relationship
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (residentFirstName != null ? residentFirstName.hashCode() : 0);
        result = 31 * result + (residentLastName != null ? residentLastName.hashCode() : 0);
        result = 31 * result + (propertyAddress != null ? propertyAddress.hashCode() : 0);
        return result;
    }
}
