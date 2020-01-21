package com.finartz.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.repository.AirlineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirlineService {
	private final AirlineRepository airlineRepository;

	public AirlineEntity save(AirlineEntity entity) {
		return airlineRepository.save(entity);
	}

	public Optional<AirlineEntity> findById(Long id) {
		return airlineRepository.findById(id);
	}

	public Optional<AirlineEntity> findByName(String name) {
		return airlineRepository.findByName(name);
	}
}
