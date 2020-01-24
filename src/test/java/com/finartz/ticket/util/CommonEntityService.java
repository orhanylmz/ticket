package com.finartz.ticket.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirplaneEntity;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.service.AirportService;
import com.finartz.ticket.service.CustomerService;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.service.FlywayService;
import com.finartz.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonEntityService {
	private final AirlineService airlineService;
	private final AirportService airportService;
	private final FlywayService flywayService;
	private final FlyService flyService;
	private final CustomerService customerService;
	private final TicketService ticketService;

	public AirportEntity generateAirportEntity() {
		String randomString = CommonUIDService.getId();

		return new AirportEntity().setCode("TR_" + randomString).setCountry("TÜRKİYE_" + randomString)
				.setProvince("TRABZON_" + randomString).setName("NM_" + randomString);
	}

	public AirlineEntity generateAirlineEntity() {
		String randomString = CommonUIDService.getId();

		return new AirlineEntity().setName("TR_" + randomString);
	}

	public AirplaneEntity generateAirplaneEntity() {
		String randomString = CommonUIDService.getId();

		AirlineEntity airline = airlineService.save(generateAirlineEntity());
		return new AirplaneEntity().setAirline(airline).setName("TR_" + randomString);
	}

	public CustomerEntity generateCustomerEntity() {
		String randomString = CommonUIDService.getId();

		return new CustomerEntity().setEmail(randomString + "@" + randomString)
				.setIdentityNumber(new Random().nextLong()).setName("N_" + randomString)
				.setSurname("SN_" + randomString).setPhone("P_" + randomString);
	}

	public FlywayEntity generateFlywayEntity() {
		AirportEntity from = airportService.save(generateAirportEntity());
		AirportEntity to = airportService.save(generateAirportEntity());
		return new FlywayEntity().setFromAirport(from).setToAirport(to);
	}

	public FlyEntity generateFlyEntity() {
		String randomString = CommonUIDService.getId();

		AirlineEntity airline = airlineService.save(generateAirlineEntity());
		FlywayEntity flyway = flywayService.save(generateFlywayEntity());
		return new FlyEntity().setAirline(airline).setFlightNumber("TK" + randomString)
				.setFlyDate(LocalDateTime.of(2020, DateUtil.now().getMonth(), 26, 12, 00)).setFlyway(flyway)
				.setPrice(new BigDecimal("250"));
	}

	public TicketEntity generateTicketEntity() {
		CustomerEntity customer = customerService.save(generateCustomerEntity());
		FlyEntity fly = flyService.save(generateFlyEntity());
		return new TicketEntity().setCustomer(customer).setFly(fly);
	}

	public CheckinEntity generateCheckinEntity() {
		TicketEntity ticket = ticketService.save(generateTicketEntity());
		return new CheckinEntity().setTicket(ticket);
	}

	public String randomString() {
		return UUID.randomUUID().toString();
	}
}
