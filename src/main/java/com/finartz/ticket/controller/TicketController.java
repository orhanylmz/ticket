package com.finartz.ticket.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finartz.ticket.aop.LogExecutionTime;
import com.finartz.ticket.dto.CheckinDTO;
import com.finartz.ticket.dto.TicketDTO;
import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.exception.InsufficientSeatException;
import com.finartz.ticket.exception.TicketNotFoundException;
import com.finartz.ticket.model.request.RequestBuyTicket;
import com.finartz.ticket.model.request.RequestCheckin;
import com.finartz.ticket.service.CheckinService;
import com.finartz.ticket.service.CustomerService;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.service.TicketService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
	private final FlyService flyService;
	private final CustomerService customerService;
	private final TicketService ticketService;
	private final CheckinService checkinService;
	private final CustomMapper mapper;

	@PostMapping("/v1/buy")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> buy(@RequestBody RequestBuyTicket request) {
		FlyEntity fly = flyService.findById(request.getFly().getId()).orElseThrow(FlyNotFoundException::new);
		if (fly.getOccupancyRate().compareTo(100) >= 0) {
			throw new InsufficientSeatException();
		}
		CustomerEntity customer = customerService.findByIdentityNumber(request.getCustomer().getIdentityNumber())
				.orElse(customerService.save(mapper.mapDtoToEntity(request.getCustomer())));
		TicketEntity ticket = ticketService.save(new TicketEntity().setCustomer(customer).setFly(fly));
		flyService.incAndSave(fly);
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@PostMapping("/v1/cancel")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> cancel(@RequestBody TicketDTO ticketDTO) {
		TicketEntity ticket = ticketService.findById(ticketDTO.getId()).orElseThrow(EntityNotFoundException::new);
		ticket = ticketService.passive(ticket);
		flyService.decAndSave(ticket.getFly());
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@GetMapping("/v1/id/{id}")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> id(@PathVariable Long id) {
		TicketEntity ticket = ticketService.findById(id).orElseThrow(EntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@PostMapping("/v1/checkin")
	@LogExecutionTime
	public ResponseEntity<CheckinDTO> checkin(@RequestBody RequestCheckin request) {
		TicketEntity ticket = ticketService.findByCustomerAndFly(request.getCustomer(), request.getFly())
				.orElseThrow(TicketNotFoundException::new);
		CheckinEntity checkin = checkinService.checkin(ticket);
		return new ResponseEntity<>(mapper.mapEntityToDto(checkin), HttpStatus.OK);
	}
}
