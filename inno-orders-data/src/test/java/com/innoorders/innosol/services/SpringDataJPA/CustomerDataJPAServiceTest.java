package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.repos.OwnerRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerDataJPAServiceTest {

    //Mocks
    @Mock
    OwnerRepo ownerRepo;


    //Concrete
    OwnerDataJPAService ownerDataJPAService;
    Customer customer;
    private final String lastName = "returned last name";



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ownerDataJPAService = new OwnerDataJPAService(ownerRepo);
        customer = new Customer();
        customer.setId(2L);
        customer.setTelephone("12344556");
        customer.setCity("Dallas");
        customer.setAddress("address is...");
        customer.setFirstName("George");
        customer.setLastName(lastName);

    }

    @Test
    public void findByLastName() {
        //add
        when(ownerRepo.findByLastName(any())).thenReturn(customer);

        //actuate
        Customer customerUnderTest = ownerDataJPAService.findByLastName(lastName);
        //assert
        assertEquals(lastName, customerUnderTest.getLastName());
        verify(ownerRepo, times(1)).findByLastName(lastName);
    }

    @Test
    public void findAll() {
        //arrange
        Set<Customer> customerSet = new HashSet<>();
        customerSet.add(customer);
        when(ownerRepo.findAll()).thenReturn(customerSet);

        //act
       Set<Customer> customerSetTest =  ownerDataJPAService.findAll();

       //assert
        assertEquals(true, customerSetTest.contains(customer));

    }

    @Test
    public void findById() {
        //arrange
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.of(customer));
        //act
        Customer customerTest = ownerDataJPAService.findById(customer.getId());
        //assert
        assertNotNull(customerTest);
        assertEquals(customer.getId(), customerTest.getId());
    }

    @Test
    public void save() {
        //arrange
        when(ownerRepo.save(any())).thenReturn(customer);
        //act
        Customer savedCustomer = ownerDataJPAService.save(customer);
        //assert
        assertEquals(customer, savedCustomer);
    }

    @Test
    public void delete() {
        //act
        ownerDataJPAService.delete(customer);
        //assert
        verify(ownerRepo, times(1)).delete(any());
    }

    @Test
    public void deleteById() {
        //act
        ownerDataJPAService.deleteById(anyLong());
        //assert
        verify(ownerRepo, times(1)).deleteById(any());
    }
}