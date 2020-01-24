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

import com.finartz.ticket.dto.FlywayDTO;
import com.finartz.ticket.util.CommonDTOService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAsync
public class FlywayControllerITest {
	private final String URL = "http://localhost:8080/flyway/v1";

	@Autowired
	private CommonDTOService commonDTOService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void should_create() {
		FlywayDTO flyway = commonDTOService.generateFlywayDTO();
		ResponseEntity<Long> response = restTemplate.postForEntity(URL + "/create", flyway, Long.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
	}

	@Test
	public void should_create_and_find_by_id() {
		FlywayDTO flyway = commonDTOService.generateFlywayDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", flyway, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		Long id = responseCreate.getBody();

		ResponseEntity<FlywayDTO> response = restTemplate.getForEntity(URL + "/id/" + id, FlywayDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getFromAirport().getId(), flyway.getFromAirport().getId());
		assertEquals(response.getBody().getFromAirport().getName(), flyway.getFromAirport().getName());
		assertEquals(response.getBody().getFromAirport().getCountry(), flyway.getFromAirport().getCountry());
		assertEquals(response.getBody().getFromAirport().getProvince(), flyway.getFromAirport().getProvince());
		assertEquals(response.getBody().getToAirport().getId(), flyway.getToAirport().getId());
		assertEquals(response.getBody().getToAirport().getName(), flyway.getToAirport().getName());
		assertEquals(response.getBody().getToAirport().getCountry(), flyway.getToAirport().getCountry());
		assertEquals(response.getBody().getToAirport().getProvince(), flyway.getToAirport().getProvince());
	}

	@Test
	public void should_create_and_find_by_from_and_to_airport() {
		FlywayDTO flyway = commonDTOService.generateFlywayDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", flyway, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		Long id = responseCreate.getBody();

		ResponseEntity<FlywayDTO> response = restTemplate.getForEntity(
				URL + "/from/" + flyway.getFromAirport().getId() + "/to/" + flyway.getToAirport().getId(),
				FlywayDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getFromAirport().getId(), flyway.getFromAirport().getId());
		assertEquals(response.getBody().getFromAirport().getName(), flyway.getFromAirport().getName());
		assertEquals(response.getBody().getFromAirport().getCountry(), flyway.getFromAirport().getCountry());
		assertEquals(response.getBody().getFromAirport().getProvince(), flyway.getFromAirport().getProvince());
		assertEquals(response.getBody().getToAirport().getId(), flyway.getToAirport().getId());
		assertEquals(response.getBody().getToAirport().getName(), flyway.getToAirport().getName());
		assertEquals(response.getBody().getToAirport().getCountry(), flyway.getToAirport().getCountry());
		assertEquals(response.getBody().getToAirport().getProvince(), flyway.getToAirport().getProvince());
	}
}
