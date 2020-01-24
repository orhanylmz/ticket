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

import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.FlywayService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class FlywayServiceITest {
	@Autowired
	private FlywayService flywayService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_flyway() {
		FlywayEntity flyway = commonEntityService.generateFlywayEntity();
		flywayService.save(flyway);

		assertNotNull(flyway);
		assertNotNull(flyway.getId());
	}

	@Test
	public void should_add_and_find_by_id() {
		FlywayEntity flyway = commonEntityService.generateFlywayEntity();
		flywayService.save(flyway);

		assertNotNull(flyway);
		assertNotNull(flyway.getId());

		FlywayEntity currentEntity = flywayService.findById(flyway.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), flyway.getId());
		assertEquals(currentEntity.getFromAirport().getId(), flyway.getFromAirport().getId());
		assertEquals(currentEntity.getToAirport().getId(), flyway.getToAirport().getId());
	}

	@Test
	public void should_add_and_find_by_from_and_to_airport() {
		FlywayEntity flyway = commonEntityService.generateFlywayEntity();
		flywayService.save(flyway);

		assertNotNull(flyway);
		assertNotNull(flyway.getId());

		FlywayEntity currentEntity = flywayService
				.findByFromAirportAndToAirport(flyway.getFromAirport().getId(), flyway.getToAirport().getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), flyway.getId());
		assertEquals(currentEntity.getFromAirport().getId(), flyway.getFromAirport().getId());
		assertEquals(currentEntity.getToAirport().getId(), flyway.getToAirport().getId());
	}
}
