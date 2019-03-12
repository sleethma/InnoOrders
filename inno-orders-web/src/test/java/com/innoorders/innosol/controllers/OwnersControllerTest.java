package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.services.OwnerService;
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
public class OwnersControllerTest {

    //Mocks
    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnersController ownersController;

    Set<Customer> customerSet;
    Customer customer = new Customer();
    Long ownerId = 2L;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        customer.setId(ownerId);
        customer.setLastName("Smith");
        customerSet = new HashSet<>();
        customerSet.add(customer);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownersController)
                .build();
    }

    @Test
    public void showOwner() throws Exception {
        Customer customer = new Customer();
        customer.setId(ownerId);
        when(ownerService.findById(anyLong())).thenReturn(customer);


        mockMvc.perform(get("/owners/2"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"));

    }

    @Test
    public void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("owners/find"))
                .andExpect(status().isOk());

        verifyZeroInteractions(ownerService);
    }
    @Test
    public void findOwnersByLastNameLikeReturnMany() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer1.setId(2L);
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                customerList);

        mockMvc.perform(get("/owners"))
                .andExpect(view().name("owners/owners-list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"));

        verify(ownerService, times(1)).findAllByLastNameLike(any());
    }

    @Test
    public void findOwnersByLastNameLikeReturnOne() throws Exception {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                customerList);

        mockMvc.perform(get("/owners"))
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(status().is3xxRedirection());

        verify(ownerService, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    public void initUpdateOwnerForm() throws Exception{
        when(ownerService.findById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/owners/"+ ownerId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("owners/create-or-update-customer-form"));

    }

    @Test
    public void processUpdateOwnerForm() throws Exception{
        when(ownerService.save(any())).thenReturn(customer);
        mockMvc.perform(post("/owners/" + ownerId + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));

        verify(ownerService, times(1)).save(any());
    }


    @Test
    public void initNewOwnerForm() throws Exception{
        mockMvc.perform(get("/owners/new"))
                .andExpect(view().name("owners/create-or-update-customer-form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"));


    }
    @Test
    public void processNewOwnerForm() throws Exception{
        when(ownerService.save(any())).thenReturn(customer);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("redirect:/owners/2"));

    }




}