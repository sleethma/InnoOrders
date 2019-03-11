package com.innoorders.innosol.bootstrap;

import com.innoorders.innosol.models.*;
import com.innoorders.innosol.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


//initializes data
@Component
@Slf4j
@Profile({"default", "H2SpringDataJPA"})
public class InitData implements CommandLineRunner {

    private OwnerService ownerService;
    private ContractorService contractorService;
    private PlanTypeService planTypeService;
    private SpecialtyService specialtyService;
    private RepairRequestService repairRequestService;

    public InitData(OwnerService ownerService, ContractorService contractorService, PlanTypeService planTypeService,
                    SpecialtyService specialtyService, RepairRequestService repairRequestService) {
        this.ownerService = ownerService;
        this.contractorService = contractorService;
        this.planTypeService = planTypeService;
        this.specialtyService = specialtyService;
        this.repairRequestService = repairRequestService;
        log.debug("Data Init Instance Created");
    }

    //will be called immediately after start-up
    @Override
    public void run(String... args) throws Exception {
        if(ownerService.findAll().size() == 0)
        loadData();
    }

    private void loadData() {
        ContractorSpecialty roofing = new ContractorSpecialty();
        roofing.setSpecialty("Roofing");
        ContractorSpecialty carpentry = new ContractorSpecialty();
        carpentry.setSpecialty("Carpentry");
        ContractorSpecialty plumbing = new ContractorSpecialty();
        plumbing.setSpecialty("Plumbing");


        //persisting Specialties to Map
        ContractorSpecialty savedRoofingSpec = specialtyService.save(roofing);
        ContractorSpecialty savedCarpentrySpec = specialtyService.save(carpentry);
        ContractorSpecialty savedPlumbingSpec = specialtyService.save(plumbing);

        PlanType threeBedroom = new PlanType();
        threeBedroom.setName("threeBedRoom");
        PlanType savedThreePlanType = planTypeService.save(threeBedroom);

        PlanType fourBedroom = new PlanType();
        fourBedroom.setName("fourBedroom");
        PlanType savedFourPlanType = planTypeService.save(fourBedroom);

        Owner testOwner1 = new Owner();
        testOwner1.setFirstName("FirstName1");
        testOwner1.setLastName("LastName1");
        testOwner1.setAddress("123 Sillyville");
        testOwner1.setCity("St. Louis");
        testOwner1.setTelephone("901-222-7676");

        Home testOwner1Home = new Home();
        testOwner1Home.setOwner(testOwner1);
        testOwner1Home.setPlanType(savedThreePlanType);
        testOwner1Home.setBuildDate(LocalDate.now());
        testOwner1Home.setPropertyAddress("374 Fountain Crest");
        testOwner1Home.setResidentFirstName("George");
        testOwner1Home.setResidentLastName("Slimter");
        testOwner1.getHomes().add(testOwner1Home);
        ownerService.save(testOwner1);


        //Repair Services
        RepairRequest testOwner1HomeRepair = new RepairRequest();
        testOwner1HomeRepair.setDate(LocalDate.now());
        testOwner1HomeRepair.setHome(testOwner1Home);
        testOwner1HomeRepair.setRepairDescription("Replaced Main Entry Wood Floor Section");
        repairRequestService.save(testOwner1HomeRepair);


        Owner dummyOwner2 = new Owner();
        dummyOwner2.setFirstName("FirstName2");
        dummyOwner2.setLastName("LastName2");
        dummyOwner2.setAddress("321 Hanson St.");
        dummyOwner2.setCity("Detroit");
        dummyOwner2.setTelephone("945-666-3444");

        Home dummyOwner2Home = new Home();
        dummyOwner2Home.setOwner(dummyOwner2);
        dummyOwner2Home.setPlanType(savedFourPlanType);
        dummyOwner2Home.setBuildDate(LocalDate.now());
        dummyOwner2Home.setPropertyAddress("764 Rightway Cr.");
        dummyOwner2Home.setResidentFirstName("Rachel");
        dummyOwner2Home.setResidentLastName("Biggs");
        dummyOwner2.getHomes().add(dummyOwner2Home);
        ownerService.save(dummyOwner2);


        Owner dummyOwner3 = new Owner();
        dummyOwner3.setFirstName("FirstName3");
        dummyOwner3.setLastName("LastName3");
        ownerService.save(dummyOwner3);

        System.out.println("Loaded Owners");

        Contractor dummyContractor1 = new Contractor();
        dummyContractor1.setFirstName("Jim");
        dummyContractor1.setLastName("Baggins");
        dummyContractor1.getSpecialties().add(savedRoofingSpec);
        contractorService.save(dummyContractor1);

        Contractor dummyContractor2 = new Contractor();
        dummyContractor2.setFirstName("Chase");
        dummyContractor2.setLastName("Chitin");
        dummyContractor2.getSpecialties().add(savedPlumbingSpec);
        dummyContractor2.getSpecialties().add(savedCarpentrySpec);
        contractorService.save(dummyContractor2);


        System.out.println("Contractors Loaded");
    }
}
