package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CityServiceTest {
    @Mock
    public ICityRepository cityRepository;

    @InjectMocks
    public CityService cityService;

    @Test
    public void find_all() {
        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City("Kigali",24), new City("Muhanga",20)));

        assertEquals("Muhanga", cityService.getAll().get(1).getName());
    }

    @Test
    public void findById() {
        when(cityRepository.findById(101L)).thenReturn(Optional.of(new City("Kigali",24)));

        assertEquals("Kigali", cityService.getById(101L).get().getName());
    }

    @Test
    public void findById_404() {
        when(cityRepository.findById(100L)).thenReturn(Optional.empty());

        assertEquals(false, cityService.getById(100L).isPresent());
    }

    @Test
    public void Name_exist_success() {
        when(cityRepository.existsByName("Kigali")).thenReturn(true);

        assertEquals(true, cityService.existsByName("Kigali"));
    }

    @Test
    public void Name_exist_fail() {
        when(cityRepository.existsByName("Kayonza")).thenReturn(false);

        assertEquals(false, cityService.existsByName("Kayonza"));
    }

    @Test
    public void createCity_404() {
        CreateCityDTO cityDTO = new CreateCityDTO("Ruhango",301);
        when(cityRepository.save(any(City.class))).thenReturn(new City(cityDTO.getName(),cityDTO.getWeather()));

        assertEquals(cityDTO.getName(), cityService.save(cityDTO).getName());
    }




}
