package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Order;
import com.innoorders.innosol.models.RepairRequest;
import com.innoorders.innosol.repos.OrderRepo;
import com.innoorders.innosol.services.RepairRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Slf4j
@Controller
public class RepairController {

    private final RepairRequestService repairRequestService;
    private final OrderRepo orderRepo;
    private final String CREATE_OR_UPDATE_REPAIR_VIEW = "repairs/create-or-update-repair-form";


    public RepairController(RepairRequestService repairRequestService, OrderRepo orderRepo) {
        this.repairRequestService = repairRequestService;
        this.orderRepo = orderRepo;
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

    @ModelAttribute("repair")
    public RepairRequest loadOrderWithRepair(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderRepo.findById(orderId).get();
        model.addAttribute("order", order);

        RepairRequest repairRequest = new RepairRequest();
        order.addRepairRequest(repairRequest);
        repairRequest.setOrder(order);
        return repairRequest;
    }

    @GetMapping("/customers/{customerId}/orders/{orderId}/repairs/new")
    public String initNewVisitForm(@PathVariable Long orderId, Model model) {
        return CREATE_OR_UPDATE_REPAIR_VIEW;
    }


    @PostMapping("/customers/{customerId}/orders/{orderId}/repairs/new")
    public String processNewRepairForm(@Valid RepairRequest repairRequest, BindingResult result) {
        if (result.hasErrors()) {
            return CREATE_OR_UPDATE_REPAIR_VIEW;
        } else {
            //todo: bug issue #6 repair description not populating
            String testRepair = repairRequest.getRepairDescription();

            repairRequestService.save(repairRequest);
            return "redirect:/customers/{customerId}";
        }
    }

}
