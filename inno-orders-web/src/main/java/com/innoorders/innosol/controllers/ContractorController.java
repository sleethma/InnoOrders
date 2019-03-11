package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Contractor;
import com.innoorders.innosol.services.ContractorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class ContractorController {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping({"contractors/index", "contractors/index.html", "contractors"})
    public String getListView(Model model){
        model.addAttribute("contractors", contractorService.findAll());
        return "contractors/index";
    }

    @GetMapping("contractors/api")
    public @ResponseBody Set<Contractor> getContractorAPI(){
        return contractorService.findAll();
    }
}
