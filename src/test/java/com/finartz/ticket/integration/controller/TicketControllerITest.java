package com.finartz.ticket.integration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

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

import com.finartz.ticket.dto.CheckinDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.dto.TicketDTO;
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.model.TicketListModel;
import com.finartz.ticket.util.CommonDTOService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAsync
public class TicketControllerITest {
	private final String URL = "http://localhost:8080/ticket/v1";

	@Autowired
	private CommonDTOService commonDTOService;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void should_buy() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		ResponseEntity<TicketDTO> response = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(response.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(response.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(response.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(response.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(response.getBody().getStatus(), TicketStatus.ACTIVE);
	}

	@Test
	public void should_buy_10() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		FlyDTO fly = ticket.getFly();
		ResponseEntity<TicketDTO> response = null;
		for (int i = 0; i < 9; i++) { // +9
			ticket = commonDTOService.generateTicketDTO();
			ticket.setFly(fly);
			response = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

			assertEquals(response.getStatusCode(), HttpStatus.OK);
			assertNotNull(response.getBody());
		}
		assertEquals(response.getBody().getFly().getPrice().longValue(), fly.getPrice().longValue());

		ticket = commonDTOService.generateTicketDTO();
		ticket.setFly(fly);
		response = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class); // +1 = %10

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(response.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(response.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(response.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(response.getBody().getStatus(), TicketStatus.ACTIVE);

		BigDecimal expectedPrice = fly.getPrice().multiply(new BigDecimal("1.1"));
		FlyDTO currentFly = response.getBody().getFly();
		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
	}

	@Test
	public void should_buy_10_and_cancel_1() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		FlyDTO fly = ticket.getFly();
		ResponseEntity<TicketDTO> response = null;
		for (int i = 0; i < 9; i++) { // +9
			ticket = commonDTOService.generateTicketDTO();
			ticket.setFly(fly);
			response = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

			assertEquals(response.getStatusCode(), HttpStatus.OK);
			assertNotNull(response.getBody());
		}
		assertEquals(response.getBody().getFly().getPrice().longValue(), fly.getPrice().longValue());

		ticket = commonDTOService.generateTicketDTO();
		ticket.setFly(fly);
		response = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class); // +1 = %10

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(response.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(response.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(response.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(response.getBody().getStatus(), TicketStatus.ACTIVE);

		BigDecimal expectedPrice = fly.getPrice().multiply(new BigDecimal("1.1"));
		FlyDTO currentFly = response.getBody().getFly();

		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());

		ticket = response.getBody();
		response = restTemplate.postForEntity(URL + "/cancel", ticket, TicketDTO.class); // 1 = %9

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(response.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(response.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(response.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(response.getBody().getStatus(), TicketStatus.PASSIVE);

		expectedPrice = fly.getPrice().multiply(new BigDecimal("1.0"));
		currentFly = response.getBody().getFly();

		assertEquals(currentFly.getPrice().longValue(), expectedPrice.longValue());
	}

	@Test
	public void should_create_and_find_by_id() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		ResponseEntity<TicketDTO> responseCreate = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseCreate.getBody());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				ticket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);

		Long id = responseCreate.getBody().getId();
		TicketDTO createdTicket = responseCreate.getBody();

		ResponseEntity<TicketDTO> response = restTemplate.getForEntity(URL + "/id/" + id, TicketDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getId(), responseCreate.getBody().getId());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				createdTicket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), createdTicket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), createdTicket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), createdTicket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);
	}

	@Test
	public void should_create_and_find_by_customer() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		ResponseEntity<TicketDTO> responseCreate = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseCreate.getBody());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				ticket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);

		TicketDTO createdTicket = responseCreate.getBody();

		ResponseEntity<TicketListModel> response = restTemplate
				.getForEntity(URL + "/customer/" + createdTicket.getCustomer().getId(), TicketListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getTicketList().size(), 1);
		assertEquals(response.getBody().getTicketList().get(0).getCustomer().getId(),
				createdTicket.getCustomer().getId());
		assertEquals(response.getBody().getTicketList().get(0).getCustomer().getIdentityNumber(),
				createdTicket.getCustomer().getIdentityNumber());
	}

	@Test
	public void should_create_and_find_by_fly() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		ResponseEntity<TicketDTO> responseCreate = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseCreate.getBody());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				ticket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);

		TicketDTO createdTicket = responseCreate.getBody();

		ResponseEntity<TicketListModel> response = restTemplate
				.getForEntity(URL + "/fly/" + createdTicket.getFly().getId(), TicketListModel.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getTicketList().size(), 1);
		assertEquals(response.getBody().getTicketList().get(0).getFly().getId(), createdTicket.getFly().getId());
		assertEquals(response.getBody().getTicketList().get(0).getFly().getFlightNumber(),
				createdTicket.getFly().getFlightNumber());
	}

	@Test
	public void should_create_and_checkin() {
		TicketDTO ticket = commonDTOService.generateTicketDTO();
		ResponseEntity<TicketDTO> responseCreate = restTemplate.postForEntity(URL + "/buy", ticket, TicketDTO.class);

		assertEquals(responseCreate.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseCreate.getBody());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				ticket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), ticket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), ticket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);

		TicketDTO createdTicket = responseCreate.getBody();

		ResponseEntity<CheckinDTO> response = restTemplate.postForEntity(URL + "/checkin", createdTicket,
				CheckinDTO.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		assertEquals(response.getBody().getTicket().getId(), responseCreate.getBody().getId());
		assertEquals(responseCreate.getBody().getCustomer().getIdentityNumber(),
				createdTicket.getCustomer().getIdentityNumber());
		assertEquals(responseCreate.getBody().getCustomer().getName(), createdTicket.getCustomer().getName());
		assertEquals(responseCreate.getBody().getFly().getId(), createdTicket.getFly().getId());
		assertEquals(responseCreate.getBody().getFly().getFlyway().getId(), createdTicket.getFly().getFlyway().getId());
		assertEquals(responseCreate.getBody().getFly().getOccupancyRate().intValue(), 1);
		assertEquals(responseCreate.getBody().getStatus(), TicketStatus.ACTIVE);
	}
}
