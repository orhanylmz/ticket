package com.finartz.ticket.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.dto.CustomerDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	private final FlyService flyService;
	private final CustomerService customerService;

	public TicketEntity save(TicketEntity entity) {
		return ticketRepository.save(entity);
	}

	public TicketEntity passive(TicketEntity entity) {
		return save(entity.setStatus(TicketStatus.PASSIVE));
	}

	public Optional<TicketEntity> findById(Long id) {
		return ticketRepository.findById(id);
	}

	public List<TicketEntity> findByCustomer(CustomerDTO customerDTO) {
		Optional<CustomerEntity> optionalCustomer = customerService.findById(customerDTO.getId());
		if (!optionalCustomer.isPresent()) {
			return Collections.emptyList();
		}
		return ticketRepository.findByCustomer(optionalCustomer.get());
	}

	public List<TicketEntity> findByFly(FlyDTO flyDTO) {
		FlyEntity fly = flyService.findById(flyDTO.getId()).orElseThrow(FlyNotFoundException::new);
		return ticketRepository.findByFly(fly);
	}
	
	public Optional<TicketEntity> findByCustomerAndFly(CustomerDTO customerDTO, FlyDTO flyDTO) {
		Optional<CustomerEntity> optionalCustomer = customerService.findById(customerDTO.getId());
		if (!optionalCustomer.isPresent()) {
			return Optional.empty();
		}
		FlyEntity fly = flyService.findById(flyDTO.getId()).orElseThrow(FlyNotFoundException::new);
		return ticketRepository.findByCustomerAndFly(optionalCustomer.get(), fly);
	}

	public List<TicketEntity> findByFlyAndStatus(FlyEntity fly, TicketStatus status) {
		return ticketRepository.findByFlyAndStatus(fly, status);
	}
}
