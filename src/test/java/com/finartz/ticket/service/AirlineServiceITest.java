package com.finartz.ticket.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class AirlineServiceITest {
	@Autowired
	private AirlineService airlineService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_airline() {
		AirlineEntity airline = commonEntityService.generateAirlineEntity();
		airlineService.save(airline);

		assertNotNull(airline);
		assertNotNull(airline.getId());
	}

	@Test
	public void should_add_and_find_by_id() {
		AirlineEntity airline = commonEntityService.generateAirlineEntity();
		airlineService.save(airline);

		assertNotNull(airline);
		assertNotNull(airline.getId());

		AirlineEntity currentEntity = airlineService.findById(airline.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airline.getId());
		assertEquals(currentEntity.getName(), airline.getName());
	}

	@Test
	public void should_add_and_find_by_name() {
		AirlineEntity airline = commonEntityService.generateAirlineEntity();
		airlineService.save(airline);

		assertNotNull(airline);
		assertNotNull(airline.getId());

		AirlineEntity currentEntity = airlineService.findByName(airline.getName())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airline.getId());
		assertEquals(currentEntity.getName(), airline.getName());
	}
}
