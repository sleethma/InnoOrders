package com.innoorders.innosol.services.SpringDataJPA;

import com.innoorders.innosol.models.Owner;
import com.innoorders.innosol.repos.OwnerRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OwnerDataJPAServiceTest {

    //Mocks
    @Mock
    OwnerRepo ownerRepo;


    //Concrete
    OwnerDataJPAService ownerDataJPAService;
    Owner owner;
    private final String lastName = "returned last name";



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ownerDataJPAService = new OwnerDataJPAService(ownerRepo);
        owner = new Owner();
        owner.setId(2L);
        owner.setTelephone("12344556");
        owner.setCity("Dallas");
        owner.setAddress("address is...");
        owner.setFirstName("George");
        owner.setLastName(lastName);

    }

    @Test
    public void findByLastName() {
        //add
        when(ownerRepo.findByLastName(any())).thenReturn(owner);

        //actuate
        Owner ownerUnderTest = ownerDataJPAService.findByLastName(lastName);
        //assert
        assertEquals(lastName, ownerUnderTest.getLastName());
        verify(ownerRepo, times(1)).findByLastName(lastName);
    }

    @Test
    public void findAll() {
        //arrange
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(owner);
        when(ownerRepo.findAll()).thenReturn(ownerSet);

        //act
       Set<Owner> ownerSetTest =  ownerDataJPAService.findAll();

       //assert
        assertEquals(true, ownerSetTest.contains(owner));

    }

    @Test
    public void findById() {
        //arrange
        when(ownerRepo.findById(anyLong())).thenReturn(Optional.of(owner));
        //act
        Owner ownerTest = ownerDataJPAService.findById(owner.getId());
        //assert
        assertNotNull(ownerTest);
        assertEquals(owner.getId(), ownerTest.getId());
    }

    @Test
    public void save() {
        //arrange
        when(ownerRepo.save(any())).thenReturn(owner);
        //act
        Owner savedOwner = ownerDataJPAService.save(owner);
        //assert
        assertEquals(owner, savedOwner);
    }

    @Test
    public void delete() {
        //act
        ownerDataJPAService.delete(owner);
        //assert
        verify(ownerRepo, times(1)).delete(any());
    }

    @Test
    public void deleteById() {
        //act
        ownerDataJPAService.deleteById(anyLong());
        //assert
        verify(ownerRepo, times(1)).deleteById(any());
    }
}