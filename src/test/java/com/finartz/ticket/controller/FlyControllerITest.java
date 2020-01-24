package com.finartz.ticket.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.model.FlyListModel;
import com.finartz.ticket.model.RequestFlyList;
import com.finartz.ticket.util.CommonDTOService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAsync
public class FlyControllerITest {
	private final String URL = "http://localhost:8080/fly/v1";

	@Autowired
	private CommonDTOService commonDTOService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void should_create() {
		FlyDTO fly = commonDTOService.generateFlyDTO();
		ResponseEntity<Long> response = restTemplate.postForEntity(URL + "/create", fly, Long.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
	}

	@Test
	public void should_create_and_find_by_id() {
		FlyDTO fly = commonDTOService.generateFlyDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", fly, Long.class);
		
		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());
		
		Long id = responseCreate.getBody();
		
		ResponseEntity<FlyDTO> response = restTemplate.getForEntity(URL + "/id/" + id, FlyDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getAirline().getId(), fly.getAirline().getId());
		assertEquals(response.getBody().getFlightNumber(), fly.getFlightNumber());
		assertEquals(response.getBody().getFlyDate(), fly.getFlyDate());
		assertEquals(response.getBody().getFlyway().getId(), fly.getFlyway().getId());
		assertEquals(response.getBody().getOccupancyRate(), fly.getOccupancyRate());
		assertEquals(response.getBody().getPrice().longValue(), fly.getPrice().longValue());
	}
	
	@Test
	public void should_create_and_find_by_flight_number() {
		FlyDTO fly = commonDTOService.generateFlyDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", fly, Long.class);
		
		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());
		
		ResponseEntity<FlyListModel> response = restTemplate.getForEntity(URL + "/flightNumber/" + fly.getFlightNumber(), FlyListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getFlyList().size(), 1);
		assertEquals(response.getBody().getFlyList().get(0).getAirline().getId(), fly.getAirline().getId());
		assertEquals(response.getBody().getFlyList().get(0).getFlightNumber(), fly.getFlightNumber());
		assertEquals(response.getBody().getFlyList().get(0).getFlyDate(), fly.getFlyDate());
		assertEquals(response.getBody().getFlyList().get(0).getFlyway().getId(), fly.getFlyway().getId());
		assertEquals(response.getBody().getFlyList().get(0).getOccupancyRate(), fly.getOccupancyRate());
		assertEquals(response.getBody().getFlyList().get(0).getPrice().longValue(), fly.getPrice().longValue());
	}
	
	@Test
	public void should_create_and_find_by_fly_list() {
		FlyDTO fly01 = commonDTOService.generateFlyDTO();
		fly01.setFlyDate(LocalDateTime.now().plusDays(2));
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", fly01, Long.class);
		
		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());
		
		FlyDTO fly02 = commonDTOService.generateFlyDTO();
		fly02.setFlyway(fly01.getFlyway());
		fly02.setFlyDate(LocalDateTime.now().plusDays(4));
		responseCreate = restTemplate.postForEntity(URL + "/create", fly02, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		RequestFlyList request = new RequestFlyList();
		request.setFlyway(fly01.getFlyway());
		request.setStart(LocalDateTime.now());
		request.setEnd(LocalDateTime.now().plusDays(5));
		ResponseEntity<FlyListModel> response = restTemplate.postForEntity(URL + "/flyList",request, FlyListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getFlyList().size(), 2);
		assertEquals(response.getBody().getFlyList().get(0).getFlyway().getId(), request.getFlyway().getId());
		assertEquals(response.getBody().getFlyList().get(1).getFlyway().getId(), request.getFlyway().getId());
	}
}
