package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.OrdersService;
import com.innoorders.innosol.services.CustomerService;
import com.innoorders.innosol.services.PlanTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Collection;

@Slf4j
@RequestMapping("/customers/{ownerId}")
@Controller
public class OrderController {


    private final OrdersService ordersService;
    private final CustomerService customerService;
    private final PlanTypeService planTypeService;

    private static final String VIEWS_ORDERS_CREATE_OR_UPDATE_FORM = "orders/create-or-update-order-form";


    public OrderController(OrdersService ordersService, CustomerService customerService, PlanTypeService planTypeService) {
        this.ordersService = ordersService;
        this.customerService = customerService;
        this.planTypeService = planTypeService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }


    @ModelAttribute("types")
    public Collection<PlanType> populatePetTypes() {
        return planTypeService.findAll();
    }

    @ModelAttribute("customer")
    public Customer findOwner(@PathVariable("ownerId") Long ownerId) {
        return customerService.findById(ownerId);
    }

    @InitBinder("customer")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/orders/new")
    public String initCreationForm(Customer customer, Model model) {
        Order order = new Order();
        customer.getOrders().add(order);
        order.setCustomer(customer);
        model.addAttribute("order", order);
        return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/orders/new")
    public String processCreationForm(Customer customer, @Valid Order order, BindingResult result, Model model) {
        if (StringUtils.hasLength(order.getResidentFirstName()) && StringUtils.hasLength(order.getResidentLastName())
                && order.isNew() && customer.getOrder(order.getResidentFirstName(), order.getResidentLastName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        customer.getOrders().add(order);
        if (result.hasErrors()) {
            model.addAttribute("order", order);
            return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
        } else {
            ordersService.save(order);
            return "redirect:/customers/" + customer.getId();
        }
    }

    @GetMapping("/orders/{orderId}/edit")
    public String initUpdateForm(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", ordersService.findById(orderId));
        return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/orders/{orderId}/edit")
    public String processUpdateForm(Customer customer, @Valid Order order, BindingResult result, Model model) {

        if (result.hasErrors()) {
            order.setCustomer(customer);
            model.addAttribute("order", order);
            return VIEWS_ORDERS_CREATE_OR_UPDATE_FORM;
        } else {
            customer.getOrders().add(order);
            ordersService.save(order);
            return "redirect:/customers/" + customer.getId();
        }
    }
}
