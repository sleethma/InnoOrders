package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Owner;
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
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owners/find";
    }

    @GetMapping("/owners/{ownerId}")
    public String showOwner(@PathVariable Long ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(ownerId));
        return "owners/ownerDetails";
    }

    @GetMapping("/owners")
    public String processFindForm(Owner owner, Model model) {
        //allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName().trim() + "%");
        if (results.isEmpty()) {
            return "owners/find";
        } else if (results.size() == 1) {
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("selections", results);
            return "owners/owners-list";
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String createOrUpdateUserForm(Model model, @PathVariable Long ownerId) {
        model.addAttribute("owner", ownerService.findById(ownerId));

        return "owners/create-or-update-owner-form";
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner,
                                         @PathVariable Long ownerId,
                                         BindingResult result) {
        if(result.hasErrors()){
            return "owners/create-or-update-owner-form";
        }
        owner.setId(ownerId);
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/owners/new")
    public String createNewOwnerForm(Model model){
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owners/create-or-update-owner-form";
    }

    @PostMapping("/owners/new")
    public String processNewOwnerForm(@Valid Owner owner, BindingResult result){
        if(result.hasErrors())return "owners/create-or-update-owner-form";
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }


}
