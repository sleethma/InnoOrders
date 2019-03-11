package com.innoorders.innosol.controllers;

import com.innoorders.innosol.models.Home;
import com.innoorders.innosol.models.RepairRequest;
import com.innoorders.innosol.repos.HomeRepo;
import com.innoorders.innosol.services.RepairRequestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RepairControllerTest {
    private final String CREATE_OR_UPDATE_REPAIR_VIEW = "repairs/create-or-update-repair-form";


    @Mock
    HomeRepo homeRepo;

    @Mock
    RepairRequestService repairRequestService;

    @InjectMocks
    RepairController repairController;

    MockMvc mockMvc;

    private URI uri;
    private UriTemplate uriTemplate = new UriTemplate("/owners/{ownerId}/homes/{homeId}/repairs/new");
    private Map<String, String> uriVars;
    Long ownerId = 1L;
    Long homeId = 2L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(repairController).build();

    }

    @Test
    public void processNewRepairFormNoErrors() throws Exception {
        Home home = new Home();
        home.setId(homeId);
        home.setBuildDate(LocalDate.of(2018,11,11));
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setRepairDescription("Repair Description Here");
        repairRequest.setId(3L);
        repairRequest.setHome(home);
        uriVars= new HashMap<>();
        uriVars.clear();
        uriVars.put("ownerId", ownerId.toString());
        uriVars.put("homeId", homeId.toString());

        uri = uriTemplate.expand(uriVars);


        //when
        when(homeRepo.findById(homeId)).thenReturn(Optional.of(home));
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("date", "2018-11-11" )
        .param("repairDescription","description again"))
                .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/{ownerId}"));


    }
}