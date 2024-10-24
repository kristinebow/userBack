package org.library.userback.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.entity.Country;
import org.library.userback.repository.CountryRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private CountryService countryService;
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        countryRepository = mock(CountryRepository.class);
        countryService = new CountryService(countryRepository);
    }

    @Test
    void testGetAllCountries() {
        Country country1 = new Country(1L, "Country A");
        Country country2 = new Country(2L, "Country B");
        List<Country> countries = Arrays.asList(country1, country2);

        when(countryRepository.findAll()).thenReturn(countries);

        List<Country> result = countryService.getAllCountries();

        assertEquals(countries, result);
    }
}