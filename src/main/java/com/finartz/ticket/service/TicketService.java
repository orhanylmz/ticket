package com.finartz.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.enumeration.TicketStatus;
import com.finartz.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;

	public TicketEntity save(TicketEntity entity) {
		return ticketRepository.save(entity);
	}

	public Optional<TicketEntity> findById(Long id) {
		return ticketRepository.findById(id);
	}

	public Optional<TicketEntity> findBySeatNumber(String seatNumber) {
		return ticketRepository.findBySeatNumber(seatNumber);
	}

	public Optional<TicketEntity> findByCustomer(CustomerEntity customer) {
		return ticketRepository.findByCustomer(customer);
	}

	public List<TicketEntity> findByFly(FlyEntity fly) {
		return ticketRepository.findByFly(fly);
	}

	public List<TicketEntity> findByFlyAndStatus(FlyEntity fly, TicketStatus status) {
		return ticketRepository.findByFlyAndStatus(fly, status);
	}
}
