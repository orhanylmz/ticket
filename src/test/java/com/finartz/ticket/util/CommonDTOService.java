package com.finartz.ticket.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.AirportDTO;
import com.finartz.ticket.dto.CustomerDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.dto.FlywayDTO;
import com.finartz.ticket.dto.TicketDTO;
import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.service.AirportService;
import com.finartz.ticket.service.CustomerService;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.service.FlywayService;

//import com.finartz.ticket.entity.AirlineEntity;
//import com.finartz.ticket.entity.AirplaneEntity;
//import com.finartz.ticket.entity.AirportEntity;
//import com.finartz.ticket.entity.CheckinEntity;
//import com.finartz.ticket.entity.CustomerEntity;
//import com.finartz.ticket.entity.FlyEntity;
//import com.finartz.ticket.entity.FlywayEntity;
//import com.finartz.ticket.entity.TicketEntity;
//import com.finartz.ticket.service.AirlineService;
//import com.finartz.ticket.service.AirportService;
//import com.finartz.ticket.service.CustomerService;
//import com.finartz.ticket.service.FlyService;
//import com.finartz.ticket.service.FlywayService;
//import com.finartz.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonDTOService {
	private final AirlineService airlineService;
	private final AirportService airportService;
	private final FlywayService flywayService;
	private final FlyService flyService;
	private final CustomerService customerService;
	private final CustomMapper mapper;

	public AirportDTO generateAirportDTO() {
		String randomString = CommonUIDService.getId();
		return new AirportDTO().setCode("TR_" + randomString).setCountry("TÜRKİYE_" + randomString)
				.setProvince("TRABZON_" + randomString).setName("NM_" + randomString);
	}

	public AirlineDTO generateAirlineDTO() {
		String randomString = CommonUIDService.getId();
		return new AirlineDTO().setName("TR_" + randomString);
	}

	public CustomerDTO generateCustomerDTO() {
		String randomString = CommonUIDService.getId();
		return new CustomerDTO().setEmail(randomString + "@" + randomString)
				.setIdentityNumber(new Random().nextLong()).setName("N_" + randomString)
				.setSurname("SN_" + randomString).setPhone("P_" + randomString);
	}

	public FlywayDTO generateFlywayDTO() {
		AirportEntity from = airportService.save(mapper.mapDtoToEntity(generateAirportDTO()));
		AirportEntity to = airportService.save(mapper.mapDtoToEntity(generateAirportDTO()));
		return new FlywayDTO().setFromAirport(mapper.mapEntityToDto(from)).setToAirport(mapper.mapEntityToDto(to));
	}

	public FlyDTO generateFlyDTO() {
		String randomString = CommonUIDService.getId();
		AirlineEntity airline = airlineService.save(mapper.mapDtoToEntity(generateAirlineDTO()));
		FlywayEntity flyway = flywayService.save(mapper.mapDtoToEntity(generateFlywayDTO()));
		return new FlyDTO().setAirline(mapper.mapEntityToDto(airline)).setFlightNumber("TK" + randomString)
				.setFlyDate(LocalDateTime.of(2020, DateUtil.now().getMonth(), 26, 12, 00))
				.setFlyway(mapper.mapEntityToDto(flyway)).setPrice(new BigDecimal("250"));
	}

	public TicketDTO generateTicketDTO() {
		CustomerEntity customer = customerService.save(mapper.mapDtoToEntity(generateCustomerDTO()));
		FlyEntity fly = flyService.save(mapper.mapDtoToEntity(generateFlyDTO()));
		return new TicketDTO().setCustomer(mapper.mapEntityToDto(customer)).setFly(mapper.mapEntityToDto(fly));
	}
}
