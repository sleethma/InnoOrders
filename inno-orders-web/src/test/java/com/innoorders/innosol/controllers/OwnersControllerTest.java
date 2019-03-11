package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Owner;
import com.innoorders.innosol.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
public class OwnersControllerTest {

    //Mocks
    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnersController ownersController;

    Set<Owner> ownerSet;
    Owner owner = new Owner();
    Long ownerId = 2L;

    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        owner.setId(ownerId);
        owner.setLastName("Smith");
        ownerSet = new HashSet<>();
        ownerSet.add(owner);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownersController)
                .build();
    }

    @Test
    public void showOwner() throws Exception {
        Owner owner = new Owner();
        owner.setId(ownerId);
        when(ownerService.findById(anyLong())).thenReturn(owner);


        mockMvc.perform(get("/owners/2"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"));

    }

    @Test
    public void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("owners/find"))
                .andExpect(status().isOk());

        verifyZeroInteractions(ownerService);
    }
    @Test
    public void findOwnersByLastNameLikeReturnMany() throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        Owner owner2 = new Owner();
        owner1.setId(2L);
        ArrayList<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner1);
        ownerList.add(owner2);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect(view().name("owners/owners-list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"));

        verify(ownerService, times(1)).findAllByLastNameLike(any());
    }

    @Test
    public void findOwnersByLastNameLikeReturnOne() throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        ArrayList<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner1);

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                ownerList);

        mockMvc.perform(get("/owners"))
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(status().is3xxRedirection());

        verify(ownerService, times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    public void initUpdateOwnerForm() throws Exception{
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/"+ ownerId + "/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/create-or-update-owner-form"));

    }

    @Test
    public void processUpdateOwnerForm() throws Exception{
        when(ownerService.save(any())).thenReturn(owner);
        mockMvc.perform(post("/owners/" + ownerId + "/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + ownerId));

        verify(ownerService, times(1)).save(any());
    }


    @Test
    public void initNewOwnerForm() throws Exception{
        mockMvc.perform(get("/owners/new"))
                .andExpect(view().name("owners/create-or-update-owner-form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"));


    }
    @Test
    public void processNewOwnerForm() throws Exception{
        when(ownerService.save(any())).thenReturn(owner);

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/2"));

    }




}