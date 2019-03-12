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

    private CustomerService customerService;
    private ContractorService contractorService;
    private PlanTypeService planTypeService;
    private SpecialtyService specialtyService;
    private RepairRequestService repairRequestService;

    public InitData(CustomerService customerService, ContractorService contractorService, PlanTypeService planTypeService,
                    SpecialtyService specialtyService, RepairRequestService repairRequestService) {
        this.customerService = customerService;
        this.contractorService = contractorService;
        this.planTypeService = planTypeService;
        this.specialtyService = specialtyService;
        this.repairRequestService = repairRequestService;
        log.debug("Data Init Instance Created");
    }

    //will be called immediately after start-up
    @Override
    public void run(String... args) throws Exception {
        if(customerService.findAll().size() == 0)
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

        Customer testCustomer1 = new Customer();
        testCustomer1.setFirstName("FirstName1");
        testCustomer1.setLastName("LastName1");
        testCustomer1.setAddress("123 Sillyville");
        testCustomer1.setCity("St. Louis");
        testCustomer1.setTelephone("901-222-7676");

        Home testOwner1Home = new Home();
        testOwner1Home.setCustomer(testCustomer1);
        testOwner1Home.setPlanType(savedThreePlanType);
        testOwner1Home.setBuildDate(LocalDate.now());
        testOwner1Home.setPropertyAddress("374 Fountain Crest");
        testOwner1Home.setResidentFirstName("George");
        testOwner1Home.setResidentLastName("Slimter");
        testCustomer1.getHomes().add(testOwner1Home);
        customerService.save(testCustomer1);


        //Repair Services
        RepairRequest testOwner1HomeRepair = new RepairRequest();
        testOwner1HomeRepair.setDate(LocalDate.now());
        testOwner1HomeRepair.setHome(testOwner1Home);
        testOwner1HomeRepair.setRepairDescription("Replaced Main Entry Wood Floor Section");
        repairRequestService.save(testOwner1HomeRepair);


        Customer dummyCustomer2 = new Customer();
        dummyCustomer2.setFirstName("FirstName2");
        dummyCustomer2.setLastName("LastName2");
        dummyCustomer2.setAddress("321 Hanson St.");
        dummyCustomer2.setCity("Detroit");
        dummyCustomer2.setTelephone("945-666-3444");

        Home dummyOwner2Home = new Home();
        dummyOwner2Home.setCustomer(dummyCustomer2);
        dummyOwner2Home.setPlanType(savedFourPlanType);
        dummyOwner2Home.setBuildDate(LocalDate.now());
        dummyOwner2Home.setPropertyAddress("764 Rightway Cr.");
        dummyOwner2Home.setResidentFirstName("Rachel");
        dummyOwner2Home.setResidentLastName("Biggs");
        dummyCustomer2.getHomes().add(dummyOwner2Home);
        customerService.save(dummyCustomer2);


        Customer dummyCustomer3 = new Customer();
        dummyCustomer3.setFirstName("FirstName3");
        dummyCustomer3.setLastName("LastName3");
        customerService.save(dummyCustomer3);

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
