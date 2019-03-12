package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Customer;
import com.innoorders.innosol.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("owners/find")
    public String findOwners(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "owners/find";
    }

    @GetMapping("/owners/{ownerId}")
    public String showOwner(@PathVariable Long ownerId, Model model) {
        model.addAttribute("customer", ownerService.findById(ownerId));
        return "owners/ownerDetails";
    }

    @GetMapping("/owners")
    public String processFindForm(Customer customer, Model model) {
        //allow parameterless GET request for /owners to return all records
        if (customer.getLastName() == null) {
            customer.setLastName("");
        }

        List<Customer> results = ownerService.findAllByLastNameLike("%" + customer.getLastName().trim() + "%");
        if (results.isEmpty()) {
            return "owners/find";
        } else if (results.size() == 1) {
            customer = results.get(0);
            return "redirect:/owners/" + customer.getId();
        } else {
            model.addAttribute("selections", results);
            return "owners/owners-list";
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String createOrUpdateUserForm(Model model, @PathVariable Long ownerId) {
        model.addAttribute("customer", ownerService.findById(ownerId));

        return "owners/create-or-update-customer-form";
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Customer customer,
                                         @PathVariable Long ownerId,
                                         BindingResult result) {
        if(result.hasErrors()){
            return "owners/create-or-update-customer-form";
        }
        customer.setId(ownerId);
        Customer savedCustomer = ownerService.save(customer);
        return "redirect:/owners/" + savedCustomer.getId();
    }

    @GetMapping("/owners/new")
    public String createNewOwnerForm(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "owners/create-or-update-customer-form";
    }

    @PostMapping("/owners/new")
    public String processNewOwnerForm(@Valid Customer customer, BindingResult result){
        if(result.hasErrors())return "owners/create-or-update-customer-form";
        Customer savedCustomer = ownerService.save(customer);
        return "redirect:/owners/" + savedCustomer.getId();
    }


}
