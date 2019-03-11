package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.models.RepairRequest;
import com.innoorders.innosol.repos.HomeRepo;
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
    private final HomeRepo homeRepo;
    private final String CREATE_OR_UPDATE_REPAIR_VIEW = "repairs/create-or-update-repair-form";


    public RepairController(RepairRequestService repairRequestService, HomeRepo homeRepo) {
        this.repairRequestService = repairRequestService;
        this.homeRepo = homeRepo;
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
    public RepairRequest loadPetWithVisit(@PathVariable("homeId") Long homeId, Model model) {
        Home home = homeRepo.findById(homeId).get();
        model.addAttribute("home", home);

        RepairRequest repairRequest = new RepairRequest();
        home.addRepairRequest(repairRequest);
        repairRequest.setHome(home);
        return repairRequest;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/{ownerId}/homes/{homeId}/repairs/new")
    public String initNewVisitForm(@PathVariable Long homeId, Model model) {
        return CREATE_OR_UPDATE_REPAIR_VIEW;
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/homes/{homeId}/repairs/new")
    public String processNewRepairForm(@Valid RepairRequest repairRequest, BindingResult result) {
        if (result.hasErrors()) {
            return CREATE_OR_UPDATE_REPAIR_VIEW;
        } else {
            //todo: bug issue #64 repair description not populating
            String testRepair = repairRequest.getRepairDescription();

            repairRequestService.save(repairRequest);
            return "redirect:/owners/{ownerId}";
        }
    }

}
