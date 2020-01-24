package com.finartz.ticket.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.util.CommonDTOService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAsync
public class AirlineControllerITest {
	private final String URL = "http://localhost:8080/airline/v1";

	@Autowired
	private CommonDTOService commonDTOService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void should_create() {
		AirlineDTO airline = commonDTOService.generateAirlineDTO();
		ResponseEntity<Long> response = restTemplate.postForEntity(URL + "/create", airline, Long.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
	}

	@Test
	public void should_create_and_find_by_id() {
		AirlineDTO airline = commonDTOService.generateAirlineDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airline, Long.class);
		
		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());
		
		Long id = responseCreate.getBody();
		
		ResponseEntity<AirlineDTO> response = restTemplate.getForEntity(URL + "/id/" + id, AirlineDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getName(), airline.getName());
	}
	
	@Test
	public void should_create_and_find_by_name() {
		AirlineDTO airline = commonDTOService.generateAirlineDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airline, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		Long id = responseCreate.getBody();

		ResponseEntity<AirlineDTO> response = restTemplate.getForEntity(URL + "/name/" + airline.getName(), AirlineDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getName(), airline.getName());
	}
}
