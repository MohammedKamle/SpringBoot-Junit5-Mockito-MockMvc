package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

//Testing of Controller/APIs using JUnit5 and Mockito

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
	
	@Mock
	CountryService countryService;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> countries ;
	Country country;
	
	@Test
	@Order(1)
	public void test_getCountries() {
		countries = new ArrayList<Country>();
		countries.add(new Country(1,"India", "Delhi"));
		countries.add(new Country(2,"USA", "Washington"));
		countries.add(new Country(3,"UK", "London"));
		when(countryService.getAllCountries()).thenReturn(countries); // Mocking
		ResponseEntity<List<Country>> res = countryController.getCountries();
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(3, res.getBody().size());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() {
		country = new Country(4, "Germany", "Berlin");
		int countryID =4;
		when(countryService.getCountryByID(countryID)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryById(countryID);
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryID, res.getBody().getId());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() {
		country = new Country(4, "Germany", "Berlin");
		String countryName = "Germany";
		when(countryService.getCountryByName(countryName)).thenReturn(country);
		ResponseEntity<Country> res = countryController.getCountryByName(countryName);
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(countryName, res.getBody().getCountryName());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() {
		country = new Country(4, "Germany", "Berlin");
		when(countryService.addCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.addCountry(country);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
		assertEquals(country, res.getBody());
		
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() {
		country = new Country(4, "Japan", "Tokyo");
		int countryId = 4;
		when(countryService.getCountryByID(countryId)).thenReturn(country);
		when(countryService.updateCountry(country)).thenReturn(country);
		ResponseEntity<Country> res = countryController.updateCountry(countryId, country);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals(4, res.getBody().getId());
		assertEquals("Japan", res.getBody().getCountryName());
		assertEquals("Tokyo", res.getBody().getCountryCapital());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		country = new Country(4, "Japan", "Tokyo");
		int countryId = 4;
		when(countryService.getCountryByID(countryId)).thenReturn(country);
		ResponseEntity<Country> res = countryController.deleteCountry(countryId);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		
	}
}
