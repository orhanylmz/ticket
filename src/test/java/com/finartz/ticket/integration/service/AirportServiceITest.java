package com.finartz.ticket.integration.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.AirportService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class AirportServiceITest {
	@Autowired
	private AirportService airportService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_airport() {
		AirportEntity airport = commonEntityService.generateAirportEntity();
		airportService.save(airport);

		assertNotNull(airport);
		assertNotNull(airport.getId());
	}

	@Test
	public void should_add_and_find_by_id() {
		AirportEntity airport = commonEntityService.generateAirportEntity();
		airportService.save(airport);

		assertNotNull(airport);
		assertNotNull(airport.getId());

		AirportEntity currentEntity = airportService.findById(airport.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airport.getId());
		assertEquals(currentEntity.getCode(), airport.getCode());
		assertEquals(currentEntity.getCountry(), airport.getCountry());
		assertEquals(currentEntity.getProvince(), airport.getProvince());
	}

	@Test
	public void should_add_and_find_by_code() {
		AirportEntity airport = commonEntityService.generateAirportEntity();
		airportService.save(airport);

		assertNotNull(airport);
		assertNotNull(airport.getId());

		AirportEntity currentEntity = airportService.findByCode(airport.getCode())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airport.getId());
		assertEquals(currentEntity.getCode(), airport.getCode());
		assertEquals(currentEntity.getCountry(), airport.getCountry());
		assertEquals(currentEntity.getProvince(), airport.getProvince());
	}

	@Test
	public void should_add_and_find_by_name() {
		AirportEntity airport = commonEntityService.generateAirportEntity();
		airportService.save(airport);

		assertNotNull(airport);
		assertNotNull(airport.getId());

		AirportEntity currentEntity = airportService.findByName(airport.getName())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airport.getId());
		assertEquals(currentEntity.getCode(), airport.getCode());
		assertEquals(currentEntity.getCountry(), airport.getCountry());
		assertEquals(currentEntity.getProvince(), airport.getProvince());
	}

	@Test
	public void should_add_and_find_by_country() {
		AirportEntity airport01 = commonEntityService.generateAirportEntity();
		airportService.save(airport01);

		assertNotNull(airport01);
		assertNotNull(airport01.getId());

		AirportEntity airport02 = commonEntityService.generateAirportEntity();
		airportService.save(airport02);

		assertNotNull(airport02);
		assertNotNull(airport02.getId());

		List<AirportEntity> entityList = airportService.findByCountry(airport01.getCountry());
		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), airport01.getId());

		entityList = airportService.findByCountry(airport02.getCountry());
		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), airport02.getId());
	}

	@Test
	public void should_add_and_find_by_country_and_province() {
		AirportEntity airport01 = commonEntityService.generateAirportEntity();
		airportService.save(airport01);
		
		assertNotNull(airport01);
		assertNotNull(airport01.getId());
		
		AirportEntity airport02 = commonEntityService.generateAirportEntity();
		airportService.save(airport02);
		
		assertNotNull(airport02);
		assertNotNull(airport02.getId());
		
		List<AirportEntity> entityList = airportService.findByCountryAndProvince(airport01.getCountry(),
				airport01.getProvince());
		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), airport01.getId());
		
		entityList = airportService.findByCountryAndProvince(airport02.getCountry(), airport02.getProvince());
		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), airport02.getId());
	}
}
