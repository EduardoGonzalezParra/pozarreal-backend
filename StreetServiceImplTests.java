package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        assertEquals(2, streets.size());
        assertEquals("id1", streets.get(0).getId());
        assertEquals("Street 1", streets.get(0).getName());
        assertEquals("id2", streets.get(1).getId());
        assertEquals("Street 2", streets.get(1).getName());
    }


    @Test
    public void givenValidStreet_whenGetStreetInfo_streetInfoIsReturned() {
        //Given
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository =  Mockito.mock(RepresentativeRepository.class);

        HousesRepository housesRepository = Mockito.mock(HousesRepository.class);
        Representative representative = new  Representative();
        StreetServiceImpl info = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);
        String streetId = "StreetID";

        Street street = new Street();
        street.setName("StreetName");

        representative.setName("RepresentativeName");
        representative.setId("RepresentativeID");
        representative.setAddress("RepresentativeAddress");
        representative.setPhone("RepresentativePhone");
        representative.setStreet("RepresentativeStreet");

        Mockito.when(streetRepository.findById(streetId)).thenReturn(Optional.of(street));
        Mockito.when(representativeRepository.findRepresentativeByStreet(streetId)).thenReturn(representative);

        //When
        StreetInfo streetInfo = info.getStreetInfo(streetId);

        //Then
        Assertions.assertEquals("StreetName", streetInfo.getName());
        Assertions.assertEquals("StreetID", streetInfo.getId());

        Assertions.assertEquals("RepresentativeName", streetInfo.getRepresentative().getName());
        Assertions.assertEquals("RepresentativeID", streetInfo.getRepresentative().getId());
        Assertions.assertEquals("RepresentativeAddress", streetInfo.getRepresentative().getAddress());
        Assertions.assertEquals("RepresentativePhone", streetInfo.getRepresentative().getPhone());
        Assertions.assertEquals("RepresentativeStreet", streetInfo.getRepresentative().getStreet());
    }


}
