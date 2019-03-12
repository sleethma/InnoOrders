package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.models.ProductType;
import com.innoorders.innosol.services.OrdersService;
import com.innoorders.innosol.services.ProductTypeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceMapTest {

    @Mock
    OrdersService homeService;

    @Mock
    ProductTypeService productTypeService;

    @Mock
    Customer customer;

    Customer unmockedCustomer;

    @Mock
    ProductType productType;

    @Mock
    Order order;

    CustomerServiceMap ownerServiceMap;
    Set<Order> orders;
    Long ownerId = 2L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Customer unmockedCustomer = new Customer();
        unmockedCustomer.setId(2L);
        ownerServiceMap = new CustomerServiceMap(homeService, productTypeService);
        orders = new HashSet<>();
        orders.add(order);

//        customer = new Customer();
//        customer.setFirstName("FirstName2");
//        customer.setLastName("LastName2");
//        customer.setAddress("321 Hanson St.");
//        customer.setCity("Detroit");
//        customer.setTelephone("945-666-3444");
    }

    @Test
    public void delete(){

    }


    @Test
    public void ownerSaveNoId(){
        Customer customerActual = ownerServiceMap.save(new Customer());
        assertNotNull(customerActual.getId());
    }

    @Test
    public void saveOwnerWithId(){
        Customer customer = new Customer();
        customer.setId(ownerId);

        Customer customerActual = ownerServiceMap.save(customer);

        assertEquals(ownerId, customerActual.getId());
    }
    @Test
    public void ownerServiceSaveOwnerWithNonNullProductType(){
        when(customer.getOrders()).thenReturn(orders);
        when(order.getProductType()).thenReturn(productType);

        ownerServiceMap.save(customer);

        verify(customer, times(orders.size())).getOrders();
        verify(productTypeService, times(orders.size())).save(productType);
    }

    @Test
    public void ownerServiceSaveOwnerWithNullProductHomeId(){
        //add
        when(customer.getOrders()).thenReturn(orders);
        when(order.getProductType()).thenReturn(productType);
        when(order.getId()).thenReturn(null);
        when(homeService.save(order)).thenReturn(order);

        //actuate
        ownerServiceMap.save(customer);

        //assert
        verify(customer, times(orders.size())).getOrders();
        verify(productTypeService, times(orders.size())).save(productType);
        verify(homeService, times(orders.size())).save(order);
        verify(order, times(orders.size())).setId(null);
    }
}