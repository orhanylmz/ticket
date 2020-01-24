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

import com.finartz.ticket.entity.AirplaneEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.AirplaneService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class AirplaneServiceITest {
	@Autowired
	private AirplaneService airplaneService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_airplane() {
		AirplaneEntity airplane = commonEntityService.generateAirplaneEntity();
		airplaneService.save(airplane);

		assertNotNull(airplane);
		assertNotNull(airplane.getId());
	}

	@Test
	public void should_add_and_find_by_id() {
		AirplaneEntity airplane = commonEntityService.generateAirplaneEntity();
		airplaneService.save(airplane);

		assertNotNull(airplane);
		assertNotNull(airplane.getId());

		AirplaneEntity currentEntity = airplaneService.findById(airplane.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airplane.getId());
		assertEquals(currentEntity.getName(), airplane.getName());
		assertEquals(currentEntity.getAirline().getId(), airplane.getAirline().getId());
		assertEquals(currentEntity.getAirline().getName(), airplane.getAirline().getName());
	}

	@Test
	public void should_add_and_find_by_name() {
		AirplaneEntity airplane = commonEntityService.generateAirplaneEntity();
		airplaneService.save(airplane);

		assertNotNull(airplane);
		assertNotNull(airplane.getId());

		AirplaneEntity currentEntity = airplaneService.findByName(airplane.getName())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), airplane.getId());
		assertEquals(currentEntity.getName(), airplane.getName());
		assertEquals(currentEntity.getAirline().getId(), airplane.getAirline().getId());
		assertEquals(currentEntity.getAirline().getName(), airplane.getAirline().getName());
	}

	@Test
	public void should_add_and_find_by_airline() {
		AirplaneEntity airplane01 = commonEntityService.generateAirplaneEntity();
		airplaneService.save(airplane01);

		assertNotNull(airplane01);
		assertNotNull(airplane01.getId());

		AirplaneEntity airplane02 = commonEntityService.generateAirplaneEntity();
		airplane02.setAirline(airplane01.getAirline());
		airplaneService.save(airplane02);

		assertNotNull(airplane02);
		assertNotNull(airplane02.getId());

		List<AirplaneEntity> entityList = airplaneService.findByAirline(airplane01.getAirline());

		assertNotNull(entityList);
		assertEquals(entityList.size(), 2);
	}

}
