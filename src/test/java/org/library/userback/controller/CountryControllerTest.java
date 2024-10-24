package org.library.userback.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.entity.Country;
import org.library.userback.services.CountryService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private List<Country> countries;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        countries = Arrays.asList(new Country(1L, "Country A"), new Country(2L, "Country B"));
    }

    @Test
    void testGetAllCountries() {
        when(countryService.getAllCountries()).thenReturn(countries);
        List<Country> result = countryController.getAllCountries();
        assertEquals(countries, result);
    }

    @Test
    void testGetAllCountriesEmpty() {
        when(countryService.getAllCountries()).thenReturn(List.of());
        List<Country> result = countryController.getAllCountries();
        assertEquals(0, result.size());
    }
}
