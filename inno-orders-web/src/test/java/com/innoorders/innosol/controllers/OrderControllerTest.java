package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.OrdersService;
import com.innoorders.innosol.services.CustomerService;
import com.innoorders.innosol.services.PlanTypeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class OrderControllerTest {

    @Mock
    OrdersService ordersService;

    @Mock
    CustomerService customerService;

    @Mock
    PlanTypeService planTypeService;

    MockMvc mockMvc;
    Customer customer;
    Set<PlanType> planTypes;

    @InjectMocks
    OrderController orderController;

    private static final String VIEWS_HOMES_CREATE_OR_UPDATE_FORM = "orders/create-or-update-order-form";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        planTypes = new HashSet<>();

         customer = new Customer();
         customer.setId(1L);
         PlanType planType1 = new PlanType();
         planType1.setName("Three Bedroom");
        PlanType planType2 = new PlanType();
        planType2.setName("Four Bedroom");
         planTypes.add(planType1);
         planTypes.add(planType2);
    }


    @Test
    public void initCreationFormTest() throws Exception {
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(planTypeService.findAll()).thenReturn(planTypes);

        mockMvc.perform(get("/customers/1/orders/new"))
                .andExpect(model().attributeExists("order"))
        .andExpect(view().name(VIEWS_HOMES_CREATE_OR_UPDATE_FORM))
        .andExpect(status().isOk());

        verify(customerService, times(1)).findById(anyLong());
        verify(planTypeService, times(1)).findAll();
    }

    @Test
    public void processCreationFormTest() throws Exception {
        Order order = new Order();
        order.setId(1L);

        when(customerService.findById(anyLong())).thenReturn(customer);
        when(planTypeService.findAll()).thenReturn(planTypes);

        mockMvc.perform(post("/customers/1/orders/new"))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/customers/1"));

        verify(ordersService).save(any());
    }

    @Test
    public void initUpdateFormTest() throws Exception {
        Order order = new Order();
        order.setId(1L);

        when(customerService.findById(anyLong())).thenReturn(customer);
        when(planTypeService.findAll()).thenReturn(planTypes);
        when(ordersService.findById(anyLong())).thenReturn(order);

        mockMvc.perform(get("/customers/1/orders/1/edit"))
                .andExpect(model().attributeExists("order"))
        .andExpect(view().name(VIEWS_HOMES_CREATE_OR_UPDATE_FORM));

        verify(customerService, times(1)).findById(anyLong());
        verify(planTypeService, times(1)).findAll();
        verify(ordersService, times(1)).findById(anyLong());
    }

    @Test
    public void processUpdateFormTest() throws Exception {
        Order order = new Order();
        order.setId(1L);

        when(customerService.findById(anyLong())).thenReturn(customer);
        when(planTypeService.findAll()).thenReturn(planTypes);
        when(ordersService.findById(anyLong())).thenReturn(order);

        mockMvc.perform(post("/customers/1/orders/1/edit"))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/customers/1"));

        verify(customerService, times(1)).findById(anyLong());
        verify(planTypeService, times(1)).findAll();
        verify(ordersService, times(1)).save(any());
    }
}