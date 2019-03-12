package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.repos.OrderRepo;
import com.innoorders.innosol.services.OrdersService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile({"SpringDataJPA", "H2SpringDataJPA"})
public class OrdersDataJPAService implements OrdersService {

    OrderRepo orderRepo;

    public OrdersDataJPAService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Set<Order> findAll() {
        Set<Order> orderSet = new HashSet<>();
        orderRepo.findAll().forEach(orderSet:: add);
        return orderSet;
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public Order save(Order object) {
        return orderRepo.save(object);
    }

    @Override
    public void delete(Order object) {
    orderRepo.delete(object);
    }

    @Override
    public void deleteById(Long id) {
    orderRepo.deleteById(id);
    }
}
