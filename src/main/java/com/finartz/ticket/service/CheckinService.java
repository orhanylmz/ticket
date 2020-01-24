package com.finartz.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.exception.CheckinAlreadyExistException;
import com.finartz.ticket.repository.CheckinRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckinService {
	private final CheckinRepository checkinRepository;

	public CheckinEntity save(CheckinEntity entity) {
		return checkinRepository.save(entity);
	}

	public Optional<CheckinEntity> findById(Long id) {
		return checkinRepository.findById(id);
	}

	public CheckinEntity checkin(TicketEntity ticket) {
		Optional<CheckinEntity> optionalCheckin = checkinRepository.findByTicket(ticket);
		if (optionalCheckin.isPresent()) {
			throw new CheckinAlreadyExistException(optionalCheckin.get());
		}
		return save(new CheckinEntity().setTicket(ticket));
	}
	
	public void removeCheckin(TicketEntity ticket) {
		Optional<CheckinEntity> optionalCheckin = checkinRepository.findByTicket(ticket);
		if (!optionalCheckin.isPresent()) {
			return;
		}
		checkinRepository.delete(optionalCheckin.get());
	}
}
