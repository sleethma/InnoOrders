package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.services.OrdersService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Profile({"default", "map"})
public class OrderServiceMap extends AbstractMapService<Order, Long> implements OrdersService {
    @Override
    public Set<Order> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Order save(Order object) {
        return super.save(object);
    }

    @Override
    public void delete(Order object) {
    super.deleteByObject(object);
    }
}
