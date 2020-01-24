package com.finartz.ticket.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.exception.CheckinAlreadyExistException;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.repository.CheckinRepository;
import com.finartz.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	private final CheckinRepository checkinRepository;
	private final FlyService flyService;
	private final CustomerService customerService;

	public TicketEntity save(TicketEntity entity) {
		return ticketRepository.save(entity);
	}

	public TicketEntity cancel(TicketEntity entity) {
		Optional<CheckinEntity> optionalCheckin = checkinRepository.findByTicket(entity);
		if (optionalCheckin.isPresent()) {
			checkinRepository.delete(optionalCheckin.get());
		}
		return save(entity.setStatus(TicketStatus.PASSIVE));
	}

	public Optional<TicketEntity> findById(Long id) {
		return ticketRepository.findById(id);
	}

	public List<TicketEntity> findByCustomerId(Long customerId) {
		Optional<CustomerEntity> optionalCustomer = customerService.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			return Collections.emptyList();
		}
		return ticketRepository.findByCustomer(optionalCustomer.get());
	}

	public List<TicketEntity> findByFlyId(Long flyId) {
		FlyEntity fly = flyService.findById(flyId).orElseThrow(FlyNotFoundException::new);
		return ticketRepository.findByFly(fly);
	}
	
	public Optional<TicketEntity> findByCustomerAndFly(Long customerId, Long flyId) {
		Optional<CustomerEntity> optionalCustomer = customerService.findById(customerId);
		if (!optionalCustomer.isPresent()) {
			return Optional.empty();
		}
		FlyEntity fly = flyService.findById(flyId).orElseThrow(FlyNotFoundException::new);
		return ticketRepository.findByCustomerAndFly(optionalCustomer.get(), fly);
	}

	public List<TicketEntity> findByFlyAndStatus(FlyEntity fly, TicketStatus status) {
		return ticketRepository.findByFlyAndStatus(fly, status);
	}
	
	public CheckinEntity checkin(TicketEntity ticket) {
		Optional<CheckinEntity> optionalCheckin = checkinRepository.findByTicket(ticket);
		if (optionalCheckin.isPresent()) {
			throw new CheckinAlreadyExistException(optionalCheckin.get());
		}
		return checkinRepository.save(new CheckinEntity().setTicket(ticket));
	}
}
