package com.finartz.ticket.service;

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

import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.TicketService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class TicketServiceITest {
	@Autowired
	private TicketService ticketService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_ticket() {
		TicketEntity ticket = commonEntityService.generateTicketEntity();
		ticketService.save(ticket);

		assertNotNull(ticket);
		assertNotNull(ticket.getId());
		assertEquals(ticket.getStatus(), TicketStatus.ACTIVE);
	}

	@Test
	public void should_add_and_cancel_ticket() {
		TicketEntity ticket = commonEntityService.generateTicketEntity();
		ticketService.save(ticket);

		assertNotNull(ticket);
		assertNotNull(ticket.getId());
		assertEquals(ticket.getStatus(), TicketStatus.ACTIVE);

		TicketEntity currentEntity = ticketService.cancel(ticket);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), ticket.getId());
		assertEquals(currentEntity.getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(currentEntity.getFly().getId(), ticket.getFly().getId());
		assertEquals(currentEntity.getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(currentEntity.getStatus(), TicketStatus.PASSIVE);
	}

	@Test
	public void should_add_and_find_by_id() {
		TicketEntity ticket = commonEntityService.generateTicketEntity();
		ticketService.save(ticket);

		assertNotNull(ticket);
		assertNotNull(ticket.getId());

		TicketEntity currentEntity = ticketService.findById(ticket.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), ticket.getId());
		assertEquals(currentEntity.getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(currentEntity.getFly().getId(), ticket.getFly().getId());
		assertEquals(currentEntity.getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
	}

	@Test
	public void should_add_and_find_by_customer() {
		TicketEntity ticket01 = commonEntityService.generateTicketEntity();
		ticketService.save(ticket01);

		assertNotNull(ticket01);
		assertNotNull(ticket01.getId());
		assertEquals(ticket01.getStatus(), TicketStatus.ACTIVE);

		TicketEntity ticket02 = commonEntityService.generateTicketEntity();
		ticket02.setCustomer(ticket01.getCustomer());
		ticketService.save(ticket02);

		assertNotNull(ticket02);
		assertNotNull(ticket02.getId());
		assertEquals(ticket02.getStatus(), TicketStatus.ACTIVE);

		List<TicketEntity> entityList = ticketService.findByCustomerId(ticket01.getCustomer().getId());

		assertNotNull(entityList);
		assertEquals(entityList.size(), 2);
		assertEquals(entityList.get(0).getCustomer().getIdentityNumber(), ticket01.getCustomer().getIdentityNumber());
		assertEquals(entityList.get(0).getFly().getId(), ticket01.getFly().getId());
		assertEquals(entityList.get(0).getFly().getFlyway().getId(), ticket01.getFly().getFlyway().getId());
		assertEquals(entityList.get(0).getStatus(), TicketStatus.ACTIVE);
	}

	@Test
	public void should_add_and_find_by_fly() {
		TicketEntity ticket01 = commonEntityService.generateTicketEntity();
		ticketService.save(ticket01);

		assertNotNull(ticket01);
		assertNotNull(ticket01.getId());
		assertEquals(ticket01.getStatus(), TicketStatus.ACTIVE);

		TicketEntity ticket02 = commonEntityService.generateTicketEntity();
		ticket02.setFly(ticket01.getFly());
		ticketService.save(ticket02);

		assertNotNull(ticket02);
		assertNotNull(ticket02.getId());
		assertEquals(ticket02.getStatus(), TicketStatus.ACTIVE);

		List<TicketEntity> entityList = ticketService.findByFlyId(ticket01.getFly().getId());

		assertNotNull(entityList);
		assertEquals(entityList.size(), 2);
		assertEquals(entityList.get(0).getCustomer().getIdentityNumber(), ticket01.getCustomer().getIdentityNumber());
		assertEquals(entityList.get(0).getFly().getId(), ticket01.getFly().getId());
		assertEquals(entityList.get(0).getFly().getFlyway().getId(), ticket01.getFly().getFlyway().getId());
		assertEquals(entityList.get(0).getStatus(), TicketStatus.ACTIVE);
	}

	@Test
	public void should_add_and_find_by_customer_and_fly() {
		TicketEntity ticket = commonEntityService.generateTicketEntity();
		ticketService.save(ticket);
		
		assertNotNull(ticket);
		assertNotNull(ticket.getId());
		assertEquals(ticket.getStatus(), TicketStatus.ACTIVE);
		
		TicketEntity currentEntity = ticketService
				.findByCustomerAndFly(ticket.getCustomer().getId(), ticket.getFly().getId())
				.orElseThrow(CustomEntityNotFoundException::new);
		
		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), ticket.getId());
		assertEquals(currentEntity.getCustomer().getIdentityNumber(), ticket.getCustomer().getIdentityNumber());
		assertEquals(currentEntity.getFly().getId(), ticket.getFly().getId());
		assertEquals(currentEntity.getFly().getFlyway().getId(), ticket.getFly().getFlyway().getId());
		assertEquals(currentEntity.getStatus(), TicketStatus.ACTIVE);
	}
	
	@Test
	public void should_add_and_checkin() {
		TicketEntity ticket = commonEntityService.generateTicketEntity();
		ticketService.save(ticket);

		assertNotNull(ticket);
		assertNotNull(ticket.getId());
		assertEquals(ticket.getStatus(), TicketStatus.ACTIVE);

		CheckinEntity currentEntity = ticketService.checkin(ticket);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getTicket().getId(), ticket.getId());
	}
}
