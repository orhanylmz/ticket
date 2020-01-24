package com.finartz.ticket.controller;

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

import com.finartz.ticket.dto.AirportDTO;
import com.finartz.ticket.model.AirportListModel;
import com.finartz.ticket.util.CommonDTOService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAsync
public class AirportControllerITest {
	private final String URL = "http://localhost:8080/airport/v1";

	@Autowired
	private CommonDTOService commonDTOService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void should_create() {
		AirportDTO airport = commonDTOService.generateAirportDTO();
		ResponseEntity<Long> response = restTemplate.postForEntity(URL + "/create", airport, Long.class);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(response.getBody());
	}

	@Test
	public void should_create_and_find_by_id() {
		AirportDTO airport = commonDTOService.generateAirportDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airport, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		Long id = responseCreate.getBody();

		ResponseEntity<AirportDTO> response = restTemplate.getForEntity(URL + "/id/" + id, AirportDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getCountry(), airport.getCountry());
		assertEquals(response.getBody().getProvince(), airport.getProvince());
		assertEquals(response.getBody().getName(), airport.getName());
		assertEquals(response.getBody().getCode(), airport.getCode());
	}

	@Test
	public void should_create_and_find_by_code() {
		AirportDTO airport = commonDTOService.generateAirportDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airport, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());
		
		Long id = responseCreate.getBody();

		ResponseEntity<AirportDTO> response = restTemplate.getForEntity(URL + "/code/" + airport.getCode(),
				AirportDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), id);
		assertEquals(response.getBody().getCountry(), airport.getCountry());
		assertEquals(response.getBody().getProvince(), airport.getProvince());
		assertEquals(response.getBody().getName(), airport.getName());
		assertEquals(response.getBody().getCode(), airport.getCode());
	}

	@Test
	public void should_create_and_find_by_country() {
		AirportDTO airport01 = commonDTOService.generateAirportDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airport01, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		AirportDTO airport02 = commonDTOService.generateAirportDTO();
		airport02.setCountry(airport01.getCountry());
		responseCreate = restTemplate.postForEntity(URL + "/create", airport02, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		ResponseEntity<AirportListModel> response = restTemplate
				.getForEntity(URL + "/country/" + airport01.getCountry(), AirportListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getAirportList());
		assertEquals(response.getBody().getAirportList().size(), 2);
		assertEquals(response.getBody().getAirportList().get(0).getCountry(), airport01.getCountry());
		assertEquals(response.getBody().getAirportList().get(1).getCountry(), airport01.getCountry());
	}

	@Test
	public void should_create_and_find_by_country_and_province() {
		AirportDTO airport01 = commonDTOService.generateAirportDTO();
		ResponseEntity<Long> responseCreate = restTemplate.postForEntity(URL + "/create", airport01, Long.class);

		AirportDTO airport02 = commonDTOService.generateAirportDTO();
		airport02.setCountry(airport01.getCountry());
		airport02.setProvince(airport01.getProvince());
		responseCreate = restTemplate.postForEntity(URL + "/create", airport02, Long.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseCreate.getBody());

		ResponseEntity<AirportListModel> response = restTemplate.getForEntity(
				URL + "/country/" + airport01.getCountry() + "/province/" + airport01.getProvince(),
				AirportListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getAirportList());
		assertEquals(response.getBody().getAirportList().size(), 2);
		assertEquals(response.getBody().getAirportList().get(0).getCountry(), airport01.getCountry());
		assertEquals(response.getBody().getAirportList().get(1).getCountry(), airport01.getCountry());
		assertEquals(response.getBody().getAirportList().get(0).getProvince(), airport01.getProvince());
		assertEquals(response.getBody().getAirportList().get(1).getProvince(), airport01.getProvince());
	}
}
