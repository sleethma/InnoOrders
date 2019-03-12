package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.SalesRep;
import com.innoorders.innosol.services.SalesRepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
@Slf4j
public class SalesRepController {

    private final SalesRepService salesRepService;

    public SalesRepController(SalesRepService salesRepService) {
        this.salesRepService = salesRepService;
    }

    @GetMapping({"sales-rep/index", "sales-rep/index.html", "sales-rep"})
    public String getListView(Model model){
        model.addAttribute("salesReps", salesRepService.findAll());
        log.debug("Number of sales reps=" + salesRepService.findAll().size());
        System.out.println("Number of sales reps=" + salesRepService.findAll().size());
        return "sales-rep/index";
    }

    @GetMapping("sales-rep/api")
    public @ResponseBody Set<SalesRep> getSalesRepAPI(){
        return salesRepService.findAll();
    }
}
