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
    private SalesRepService salesRepService;
    private ProductTypeService productTypeService;
    private SpecialtyService specialtyService;
    private RepairRequestService repairRequestService;

    public InitData(CustomerService customerService, SalesRepService salesRepService, ProductTypeService productTypeService,
                    SpecialtyService specialtyService, RepairRequestService repairRequestService) {
        this.customerService = customerService;
        this.salesRepService = salesRepService;
        this.productTypeService = productTypeService;
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
        SalesRepSpecialty roofing = new SalesRepSpecialty();
        roofing.setSpecialty("Roofing");
        SalesRepSpecialty carpentry = new SalesRepSpecialty();
        carpentry.setSpecialty("Carpentry");
        SalesRepSpecialty plumbing = new SalesRepSpecialty();
        plumbing.setSpecialty("Plumbing");


        //persisting Specialties to Map
        SalesRepSpecialty savedRoofingSpec = specialtyService.save(roofing);
        SalesRepSpecialty savedCarpentrySpec = specialtyService.save(carpentry);
        SalesRepSpecialty savedPlumbingSpec = specialtyService.save(plumbing);

        ProductType threeBedroom = new ProductType();
        threeBedroom.setName("threeBedRoom");
        ProductType savedThreeProductType = productTypeService.save(threeBedroom);

        ProductType fourBedroom = new ProductType();
        fourBedroom.setName("fourBedroom");
        ProductType savedFourProductType = productTypeService.save(fourBedroom);

        Customer testCustomer1 = new Customer();
        testCustomer1.setFirstName("FirstName1");
        testCustomer1.setLastName("LastName1");
        testCustomer1.setAddress("123 Sillyville");
        testCustomer1.setCity("St. Louis");
        testCustomer1.setTelephone("901-222-7676");

        Order testOwner1Order = new Order();
        testOwner1Order.setCustomer(testCustomer1);
        testOwner1Order.setProductType(savedThreeProductType);
        testOwner1Order.setPurchaseDate(LocalDate.now());
        testOwner1Order.setPropertyAddress("374 Fountain Crest");
        testOwner1Order.setResidentFirstName("George");
        testOwner1Order.setResidentLastName("Slimter");
        testCustomer1.getOrders().add(testOwner1Order);
        customerService.save(testCustomer1);


        //Repair Services
        RepairRequest testOwner1HomeRepair = new RepairRequest();
        testOwner1HomeRepair.setDate(LocalDate.now());
        testOwner1HomeRepair.setOrder(testOwner1Order);
        testOwner1HomeRepair.setRepairDescription("Replaced Main Entry Wood Floor Section");
        repairRequestService.save(testOwner1HomeRepair);


        Customer dummyCustomer2 = new Customer();
        dummyCustomer2.setFirstName("FirstName2");
        dummyCustomer2.setLastName("LastName2");
        dummyCustomer2.setAddress("321 Hanson St.");
        dummyCustomer2.setCity("Detroit");
        dummyCustomer2.setTelephone("945-666-3444");

        Order dummyOwner2Order = new Order();
        dummyOwner2Order.setCustomer(dummyCustomer2);
        dummyOwner2Order.setProductType(savedFourProductType);
        dummyOwner2Order.setPurchaseDate(LocalDate.now());
        dummyOwner2Order.setPropertyAddress("764 Rightway Cr.");
        dummyOwner2Order.setResidentFirstName("Rachel");
        dummyOwner2Order.setResidentLastName("Biggs");
        dummyCustomer2.getOrders().add(dummyOwner2Order);
        customerService.save(dummyCustomer2);


        Customer dummyCustomer3 = new Customer();
        dummyCustomer3.setFirstName("FirstName3");
        dummyCustomer3.setLastName("LastName3");
        customerService.save(dummyCustomer3);

        System.out.println("Loaded Owners");

        SalesRep dummySalesRep1 = new SalesRep();
        dummySalesRep1.setFirstName("Jim");
        dummySalesRep1.setLastName("Baggins");
        dummySalesRep1.getSpecialties().add(savedRoofingSpec);
        salesRepService.save(dummySalesRep1);

        SalesRep dummySalesRep2 = new SalesRep();
        dummySalesRep2.setFirstName("Chase");
        dummySalesRep2.setLastName("Chitin");
        dummySalesRep2.getSpecialties().add(savedPlumbingSpec);
        dummySalesRep2.getSpecialties().add(savedCarpentrySpec);
        salesRepService.save(dummySalesRep2);


        System.out.println("Sales Representatives Loaded");
    }
}
