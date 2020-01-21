package com.finartz.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.repository.FlywayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlywayService {
	private final FlywayRepository flywayRepository;

	public FlywayEntity save(FlywayEntity entity) {
		return flywayRepository.save(entity);
	}

	public Optional<FlywayEntity> findById(Long id) {
		return flywayRepository.findById(id);
	}

	public Optional<FlywayEntity> findByFromAirportAndToAirport(AirportEntity fromAirport, AirportEntity toAirport) {
		return flywayRepository.findByFromAirportAndToAirport(fromAirport, toAirport);
	}

	public List<FlywayEntity> findByFromAirport(AirportEntity fromAirport) {
		return flywayRepository.findByFromAirport(fromAirport);
	}

	public List<FlywayEntity> findByToAirport(AirportEntity toAirport) {
		return flywayRepository.findByFromAirport(toAirport);
	}
}
