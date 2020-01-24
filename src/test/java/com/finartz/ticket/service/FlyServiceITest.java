package com.finartz.ticket.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.util.CommonEntityService;
import com.finartz.ticket.util.DateUtil;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class FlyServiceITest {
	@Autowired
	private FlyService flyService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_fly() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());
	}

	@Test
	public void should_buy_ticket() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());

		int oldOccupancyRate = fly.getOccupancyRate();
		FlyEntity currentFly = flyService.incAndSave(fly);
		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getOccupancyRate(), oldOccupancyRate + 1);
	}

	@Test
	public void should_buy_and_buy_and_cancel_ticket() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());

		fly = flyService.incAndSave(fly);
		assertNotNull(fly);
		assertNotNull(fly.getId());

		fly = flyService.incAndSave(fly);
		assertNotNull(fly);
		assertNotNull(fly.getId());

		int oldOccupancyRate = fly.getOccupancyRate();
		FlyEntity currentFly = flyService.decAndSave(fly);

		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getOccupancyRate(), oldOccupancyRate - 1);
	}

	@Test
	public void should_add_and_find_by_id() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());

		FlyEntity currentEntity = flyService.findById(fly.getId()).orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), fly.getId());
		assertEquals(currentEntity.getAirline().getId(), fly.getAirline().getId());
		assertEquals(currentEntity.getFlightNumber(), fly.getFlightNumber());
		assertEquals(currentEntity.getFlyDate(), fly.getFlyDate());
		assertEquals(currentEntity.getFlyway().getId(), fly.getFlyway().getId());
		assertEquals(currentEntity.getOccupancyRate(), fly.getOccupancyRate());
		assertEquals(currentEntity.getOriginalPrice().longValue(), fly.getOriginalPrice().longValue());
		assertEquals(currentEntity.getPrice().longValue(), fly.getPrice().longValue());
	}

	@Test
	public void should_add_and_find_by_flightNumber() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());

		List<FlyEntity> entityList = flyService.findByFlightNumber(fly.getFlightNumber());

		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), fly.getId());
		assertEquals(entityList.get(0).getAirline().getId(), fly.getAirline().getId());
		assertEquals(entityList.get(0).getFlightNumber(), fly.getFlightNumber());
		assertEquals(entityList.get(0).getFlyDate(), fly.getFlyDate());
		assertEquals(entityList.get(0).getFlyway().getId(), fly.getFlyway().getId());
		assertEquals(entityList.get(0).getOccupancyRate(), fly.getOccupancyRate());
		assertEquals(entityList.get(0).getOriginalPrice().longValue(), fly.getOriginalPrice().longValue());
		assertEquals(entityList.get(0).getPrice().longValue(), fly.getPrice().longValue());
	}

	@Test
	public void should_add_and_find_by_flayway_and_date_between() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());

		List<FlyEntity> entityList = flyService.findByFlywayAndDateBetween(fly.getFlyway().getId(), DateUtil.now(),
				LocalDateTime.of(2020, (DateUtil.now().getMonth()), 31, 12, 00));

		assertNotNull(entityList);
		assertEquals(entityList.size(), 1);
		assertEquals(entityList.get(0).getId(), fly.getId());
		assertEquals(entityList.get(0).getAirline().getId(), fly.getAirline().getId());
		assertEquals(entityList.get(0).getFlightNumber(), fly.getFlightNumber());
		assertEquals(entityList.get(0).getFlyDate(), fly.getFlyDate());
		assertEquals(entityList.get(0).getFlyway().getId(), fly.getFlyway().getId());
		assertEquals(entityList.get(0).getOccupancyRate(), fly.getOccupancyRate());
		assertEquals(entityList.get(0).getOriginalPrice().longValue(), fly.getOriginalPrice().longValue());
		assertEquals(entityList.get(0).getPrice().longValue(), fly.getPrice().longValue());
	}

	@Test
	public void should_add_and_buy_10_and_buy_10_ticket() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());
		
		//First 10

		BigDecimal oldPrice = fly.getPrice();
		FlyEntity currentFly = null;
		for (int i = 0; i < 9; i++) {
			currentFly = flyService.incAndSave(fly);
		}

		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		assertEquals(currentFly.getPrice().longValue(), oldPrice.longValue());

		currentFly = flyService.incAndSave(fly);
		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		BigDecimal expectedPrice = fly.getOriginalPrice().multiply(new BigDecimal("1.1"));
		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
		
		//Second 10
		
		oldPrice = fly.getPrice();
		currentFly = null;
		for (int i = 0; i < 9; i++) {
			currentFly = flyService.incAndSave(fly);
		}

		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		assertEquals(currentFly.getPrice().longValue(), oldPrice.longValue());

		currentFly = flyService.incAndSave(fly);
		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		expectedPrice = fly.getOriginalPrice().multiply(new BigDecimal("1.2"));
		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
	}
	
	@Test
	public void should_add_and_buy_10_and_cancel_1_ticket() {
		FlyEntity fly = commonEntityService.generateFlyEntity();
		flyService.save(fly);

		assertNotNull(fly);
		assertNotNull(fly.getId());
		
		//Buy 10

		BigDecimal oldPrice = fly.getPrice();
		FlyEntity currentFly = null;
		for (int i = 0; i < 9; i++) {
			currentFly = flyService.incAndSave(fly);
		}

		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		assertEquals(currentFly.getPrice().longValue(), oldPrice.longValue());

		currentFly = flyService.incAndSave(fly);
		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		BigDecimal expectedPrice = fly.getOriginalPrice().multiply(new BigDecimal("1.1"));
		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
		
		//Cancel 1
		
		oldPrice = fly.getPrice();
		currentFly = flyService.decAndSave(fly);

		assertNotNull(currentFly.getId());
		assertEquals(currentFly.getId(), fly.getId());
		expectedPrice = fly.getOriginalPrice().multiply(new BigDecimal("1.0"));
		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
	}
}
