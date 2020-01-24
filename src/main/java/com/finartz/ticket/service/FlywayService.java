package com.finartz.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.repository.FlywayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlywayService {
	private final AirportService airportService;
	private final FlywayRepository flywayRepository;

	public FlywayEntity save(FlywayEntity entity) {
		return flywayRepository.save(entity);
	}

	public Optional<FlywayEntity> findById(Long id) {
		return flywayRepository.findById(id);
	}

	public Optional<FlywayEntity> findByFromAirportAndToAirport(Long fromAirportId, Long toAirportId) {
		AirportEntity from = airportService.findById(fromAirportId).orElseThrow(CustomEntityNotFoundException::new);
		AirportEntity to = airportService.findById(toAirportId).orElseThrow(CustomEntityNotFoundException::new);
		return flywayRepository.findByFromAirportAndToAirport(from, to);
	}
}
