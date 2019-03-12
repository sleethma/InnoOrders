package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.services.OrdersService;
import com.innoorders.innosol.services.CustomerService;
import com.innoorders.innosol.services.PlanTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Profile({"default", "map"})
public class CustomerServiceMap extends AbstractMapService<Customer, Long> implements CustomerService {

    private final OrdersService homeService;
    private final PlanTypeService planTypeService;

    public CustomerServiceMap(OrdersService homeService, PlanTypeService planTypeService) {
        this.homeService = homeService;
        this.planTypeService = planTypeService;
    }

    @Override
    public Set<Customer> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
    super.deleteById(id);
    }

    @Override
    public Customer save(Customer object) {
        if (object != null) {
            object.getOrders().forEach(home -> {
                if (home.getPlanType() != null) {
                    home.setPlanType(planTypeService.save(home.getPlanType()));
                } else {
                    throw new RuntimeException("Must have a plan type for order");
                }

                if (home.getId() == null) {
                    Order savedOrder = homeService.save(home);
                    home.setId(savedOrder.getId());
                }
            });
            return super.save(object);
        }else{
            return null;
        }
    }

    @Override
    public void delete(Customer object) {
    super.deleteByObject(object);
    }

    @Override
    public Customer findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Customer findByLastName(String lastName) {
        return null;
    }

    @Override
    public List<Customer> findAllByLastNameLike(String lastName) {
       //todo implement this
        return null;
    }
}
