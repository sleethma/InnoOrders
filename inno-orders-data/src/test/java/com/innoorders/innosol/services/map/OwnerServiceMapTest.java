package com.innoorders.innosol.services.map;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.models.Owner;
import com.innoorders.innosol.models.PlanType;
import com.innoorders.innosol.services.HomesService;
import com.innoorders.innosol.services.PlanTypeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OwnerServiceMapTest {

    @Mock
    HomesService homeService;

    @Mock
    PlanTypeService planTypeService;

    @Mock
    Owner owner;

    Owner unmockedOwner;

    @Mock
    PlanType planType;

    @Mock
    Home home;

    OwnerServiceMap ownerServiceMap;
    Set<Home> homes;
    Long ownerId = 2L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Owner unmockedOwner = new Owner();
        unmockedOwner.setId(2L);
        ownerServiceMap = new OwnerServiceMap(homeService, planTypeService);
        homes = new HashSet<>();
        homes.add(home);

//        owner = new Owner();
//        owner.setFirstName("FirstName2");
//        owner.setLastName("LastName2");
//        owner.setAddress("321 Hanson St.");
//        owner.setCity("Detroit");
//        owner.setTelephone("945-666-3444");
    }

    @Test
    public void delete(){

    }


    @Test
    public void ownerSaveNoId(){
        Owner ownerActual = ownerServiceMap.save(new Owner());
        assertNotNull(ownerActual.getId());
    }

    @Test
    public void saveOwnerWithId(){
        Owner owner = new Owner();
        owner.setId(ownerId);

        Owner ownerActual = ownerServiceMap.save(owner);

        assertEquals(ownerId, ownerActual.getId());
    }
    @Test
    public void ownerServiceSaveOwnerWithNonNullPlanType(){
        when(owner.getHomes()).thenReturn(homes);
        when(home.getPlanType()).thenReturn(planType);

        ownerServiceMap.save(owner);

        verify(owner, times(homes.size())).getHomes();
        verify(planTypeService, times(homes.size())).save(planType);
    }

    @Test
    public void ownerServiceSaveOwnerWithNullPlanHomeId(){
        //add
        when(owner.getHomes()).thenReturn(homes);
        when(home.getPlanType()).thenReturn(planType);
        when(home.getId()).thenReturn(null);
        when(homeService.save(home)).thenReturn(home);

        //actuate
        ownerServiceMap.save(owner);

        //assert
        verify(owner, times(homes.size())).getHomes();
        verify(planTypeService, times(homes.size())).save(planType);
        verify(homeService, times(homes.size())).save(home);
        verify(home, times(homes.size())).setId(null);
    }
}