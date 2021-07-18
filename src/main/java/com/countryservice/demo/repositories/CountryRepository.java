package com.countryservice.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countryservice.demo.beans.Country;

// JpaRepository accepts the POJO bean class and data-type of primary key in POJO
public interface CountryRepository extends JpaRepository<Country, Integer>{ 

}
