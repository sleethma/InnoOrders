package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.models.Owner;
import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.HomesService;
import com.innoorders.innosol.services.OwnerService;
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
@RequestMapping("/owners/{ownerId}")
@Controller
public class HomeController {


    private final HomesService homesService;
    private final OwnerService ownerService;
    private final PlanTypeService planTypeService;

    private static final String VIEWS_HOMES_CREATE_OR_UPDATE_FORM = "homes/create-or-update-home-form";


    public HomeController(HomesService homesService, OwnerService ownerService, PlanTypeService planTypeService) {
        this.homesService = homesService;
        this.ownerService = ownerService;
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

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/homes/new")
    public String initCreationForm(Owner owner, Model model) {
        Home home = new Home();
        owner.getHomes().add(home);
        home.setOwner(owner);
        model.addAttribute("home", home);
        return VIEWS_HOMES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/homes/new")
    public String processCreationForm(Owner owner, @Valid Home home, BindingResult result, Model model) {
        if (StringUtils.hasLength(home.getResidentFirstName()) && StringUtils.hasLength(home.getResidentLastName())
                && home.isNew() && owner.getHome(home.getResidentFirstName(), home.getResidentLastName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getHomes().add(home);
        if (result.hasErrors()) {
            model.addAttribute("home", home);
            return VIEWS_HOMES_CREATE_OR_UPDATE_FORM;
        } else {
            homesService.save(home);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/homes/{homeId}/edit")
    public String initUpdateForm(@PathVariable Long homeId, Model model) {
        model.addAttribute("home", homesService.findById(homeId));
        return VIEWS_HOMES_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/homes/{homeId}/edit")
    public String processUpdateForm(Owner owner, @Valid Home home, BindingResult result, Model model) {

        if (result.hasErrors()) {
            home.setOwner(owner);
            model.addAttribute("home", home);
            return VIEWS_HOMES_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getHomes().add(home);
            homesService.save(home);
            return "redirect:/owners/" + owner.getId();
        }
    }
}
