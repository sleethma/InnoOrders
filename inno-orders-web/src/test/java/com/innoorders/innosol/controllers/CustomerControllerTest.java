package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
public class CustomerControllerTest {

    //Mocks
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    Set<Customer> customerSet;
    Customer customer = new Customer();
    Long customerId = 2L;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        customer.setId(customerId);
        customer.setLastName("Smith");
        customerSet = new HashSet<>();
        customerSet.add(customer);

        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @Test
    public void showCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerService.findById(anyLong())).thenReturn(customer);


        mockMvc.perform(get("/customers/2"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customerDetails"));

    }

    @Test
    public void findCustomers() throws Exception {
        mockMvc.perform(get("/customers/find"))
                .andExpect(view().name("customers/find"))
                .andExpect(status().isOk());

        verifyZeroInteractions(customerService);
    }
    @Test
    public void findCustomersByLastNameLikeReturnMany() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer1.setId(2L);
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        when(customerService.findAllByLastNameLike(anyString())).thenReturn(
                customerList);

        mockMvc.perform(get("/customers"))
                .andExpect(view().name("customers/customers-list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"));

        verify(customerService, times(1)).findAllByLastNameLike(any());
    }

    @Test
    public void findCustomersByLastNameLikeReturnOne() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);

        when(customerService.findAllByLastNameLike(anyString())).thenReturn(
                customerList);

        mockMvc.perform(get("/customers"))
                .andExpect(view().name("redirect:/customers/1"))
                .andExpect(status().is3xxRedirection());

        verify(customerService, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    public void initUpdateCustomerForm() throws Exception{
        when(customerService.findById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/customers/"+ customerId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("customers/create-or-update-customer-form"));

    }

    @Test
    public void processUpdateCustomerForm() throws Exception{
        when(customerService.save(any())).thenReturn(customer);
        mockMvc.perform(post("/customers/" + customerId + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/" + customerId));

        verify(customerService, times(1)).save(any());
    }


    @Test
    public void initNewCustomerForm() throws Exception{
        mockMvc.perform(get("/customers/new"))
                .andExpect(view().name("customers/create-or-update-customer-form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"));


    }
    @Test
    public void processNewCustomerForm() throws Exception{
        when(customerService.save(any())).thenReturn(customer);

        mockMvc.perform(post("/customers/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("redirect:/customers/2"));

    }




}