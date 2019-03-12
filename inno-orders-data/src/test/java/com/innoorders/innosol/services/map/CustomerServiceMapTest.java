package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.HomesService;
import com.innoorders.innosol.services.PlanTypeService;

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
    HomesService homeService;

    @Mock
    PlanTypeService planTypeService;

    @Mock
    Customer customer;

    Customer unmockedCustomer;

    @Mock
    PlanType planType;

    @Mock
    Home home;

    CustomerServiceMap ownerServiceMap;
    Set<Home> homes;
    Long ownerId = 2L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Customer unmockedCustomer = new Customer();
        unmockedCustomer.setId(2L);
        ownerServiceMap = new CustomerServiceMap(homeService, planTypeService);
        homes = new HashSet<>();
        homes.add(home);

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
    public void ownerServiceSaveOwnerWithNonNullPlanType(){
        when(customer.getHomes()).thenReturn(homes);
        when(home.getPlanType()).thenReturn(planType);

        ownerServiceMap.save(customer);

        verify(customer, times(homes.size())).getHomes();
        verify(planTypeService, times(homes.size())).save(planType);
    }

    @Test
    public void ownerServiceSaveOwnerWithNullPlanHomeId(){
        //add
        when(customer.getHomes()).thenReturn(homes);
        when(home.getPlanType()).thenReturn(planType);
        when(home.getId()).thenReturn(null);
        when(homeService.save(home)).thenReturn(home);

        //actuate
        ownerServiceMap.save(customer);

        //assert
        verify(customer, times(homes.size())).getHomes();
        verify(planTypeService, times(homes.size())).save(planType);
        verify(homeService, times(homes.size())).save(home);
        verify(home, times(homes.size())).setId(null);
    }
}