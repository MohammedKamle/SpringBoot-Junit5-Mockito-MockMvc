package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

// Testing of Microservice using JUnit5 and Mockito

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTest.class})
public class ServiceMockitoTest {
	
	@Mock
	CountryRepository countryrep;
	
	@InjectMocks
	CountryService countryService;
	
	@Test
	@Order(1)
	public void test_getAllCountries() {
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1,"India", "Delhi"));
		countries.add(new Country(2,"USA", "Washington"));
		countries.add(new Country(3,"UK", "London"));
		when(countryrep.findAll()).thenReturn(countries); // Mocking
		assertEquals(3, countryService.getAllCountries().size());
	}
	
	
	@Test
	@Order(2)
	public void test_getCountryByID() {
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1,"India", "Delhi"));
		countries.add(new Country(2,"USA", "Washington"));
		countries.add(new Country(3,"UK", "London"));
		int countryID =2;
		when(countryrep.findAll()).thenReturn(countries); //Mocking
		assertEquals(countryID, countryService.getCountryByID(countryID).getId());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country(1,"India", "Delhi"));
		countries.add(new Country(2,"USA", "Washington"));
		countries.add(new Country(3,"UK", "London"));
		String countryName = "India";
		when(countryrep.findAll()).thenReturn(countries); //Mocking
		assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		Country country = new Country(4, "Germany", "Berlin");
		when(countryrep.save(country)).thenReturn(country);
		assertEquals(country, countryService.addCountry(country));
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		Country country = new Country(4, "Germany", "Berlin");
		when(countryrep.save(country)).thenReturn(country);
		assertEquals(country, countryService.updateCountry(country));
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		Country country = new Country(4, "Germany", "Berlin");
		countryService.deleteCountry(country);
		// as deleteCountry() method doesn't return anything we cant use when() and thenReturn(), here
		// we will verify whether coutryrep.delete() is invoked once  in deleteCountry() method of CountryService class by following way
		verify(countryrep, times(1)).delete(country);
	}
	
	
	
}
