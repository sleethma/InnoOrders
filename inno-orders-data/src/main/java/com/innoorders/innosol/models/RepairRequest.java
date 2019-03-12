package com.innoorders.innosol.models;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j
@Data
@Entity
@Table(name = "repairs")
public class RepairRequest extends BaseEntity{

    @Column(name = "date")
    @CreationTimestamp
    private LocalDate date;

    @Column(name = "description")
    private String repairDescription;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Order order;

    @Override
    public String toString() {
        return "RepairRequest{" +
                "date=" + date +
                ", repairDescription='" + repairDescription + '\'' +
                '}';
    }
}
