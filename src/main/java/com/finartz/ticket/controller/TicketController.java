package com.finartz.ticket.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.exception.InsufficientSeatException;
import com.finartz.ticket.exception.TicketNotFoundException;
import com.finartz.ticket.model.TicketListModel;
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
	private final CustomMapper mapper;

	@PostMapping("/v1/buy")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> buy(@RequestBody TicketDTO ticketDTO) {
		FlyEntity fly = flyService.findById(ticketDTO.getFly().getId()).orElseThrow(FlyNotFoundException::new);
		if (fly.getOccupancyRate().compareTo(100) >= 0) {
			throw new InsufficientSeatException();
		}
		CustomerEntity customer = customerService.findByIdentityNumber(ticketDTO.getCustomer().getIdentityNumber())
				.orElse(customerService.save(mapper.mapDtoToEntity(ticketDTO.getCustomer())));
		TicketEntity ticket = ticketService.save(new TicketEntity().setCustomer(customer).setFly(fly));
		flyService.incAndSave(fly);
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@PostMapping("/v1/cancel")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> cancel(@RequestBody TicketDTO ticketDTO) {
		TicketEntity ticket = ticketService.findById(ticketDTO.getId()).orElseThrow(CustomEntityNotFoundException::new);
		ticket = ticketService.cancel(ticket);
		flyService.decAndSave(ticket.getFly());
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@GetMapping("/v1/id/{id}")
	@LogExecutionTime
	public ResponseEntity<TicketDTO> id(@PathVariable Long id) {
		TicketEntity ticket = ticketService.findById(id).orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(ticket), HttpStatus.OK);
	}

	@GetMapping("/v1/customer/{customerId}")
	@LogExecutionTime
	public ResponseEntity<TicketListModel> customer(@PathVariable Long customerId) {
		List<TicketEntity> ticketList = ticketService.findByCustomerId(customerId);
		List<TicketDTO> ticketDTOList = ticketList.stream().map(ticket -> mapper.mapEntityToDto(ticket))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new TicketListModel().setTicketList(ticketDTOList), HttpStatus.OK);
	}
	
	@GetMapping("/v1/fly/{flyId}")
	@LogExecutionTime
	public ResponseEntity<TicketListModel> flyId(@PathVariable Long flyId) {
		List<TicketEntity> ticketList = ticketService.findByFlyId(flyId);
		List<TicketDTO> ticketDTOList = ticketList.stream().map(ticket -> mapper.mapEntityToDto(ticket))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new TicketListModel().setTicketList(ticketDTOList), HttpStatus.OK);
	}

	@PostMapping("/v1/checkin")
	@LogExecutionTime
	public ResponseEntity<CheckinDTO> checkin(@RequestBody TicketDTO ticketDTO) {
		TicketEntity ticket = ticketService.findById(ticketDTO.getId()).orElseThrow(TicketNotFoundException::new);
		if(ticket.getStatus().equals(TicketStatus.PASSIVE)) {
			throw new TicketNotFoundException();
		}
		CheckinEntity checkin = ticketService.checkin(ticket);
		return new ResponseEntity<>(mapper.mapEntityToDto(checkin), HttpStatus.OK);
	}
}
