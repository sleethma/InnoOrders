package com.innoorders.innosol.repos;

import com.innoorders.innosol.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {
}
