package com.finartz.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.dto.FlywayDTO;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.repository.FlywayRepository;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlywayService {
	private final AirportService airportService;
	private final FlywayRepository flywayRepository;
	private final CustomMapper mapper;

	public FlywayEntity save(FlywayEntity entity) {
		return flywayRepository.save(entity);
	}

	public FlywayEntity update(FlywayDTO dto) {
		FlywayEntity flyway = findById(dto.getId()).orElseThrow(CustomEntityNotFoundException::new);
		mapper.updateEntity(dto, flyway);
		return save(flyway);
	}

	public Optional<FlywayEntity> findById(Long id) {
		return flywayRepository.findById(id);
	}

	public Optional<FlywayEntity> findByFromAirportAndToAirport(Long fromAirportId, Long toAirportId) {
		AirportEntity from = airportService.findById(fromAirportId).orElseThrow(CustomEntityNotFoundException::new);
		AirportEntity to = airportService.findById(toAirportId).orElseThrow(CustomEntityNotFoundException::new);
		return flywayRepository.findByFromAirportAndToAirport(from, to);
	}

	public List<FlywayEntity> findByFromAirport(Long fromAirportId) {
		AirportEntity from = airportService.findById(fromAirportId).orElseThrow(CustomEntityNotFoundException::new);
		return flywayRepository.findByFromAirport(from);
	}

	public List<FlywayEntity> findByToAirport(Long toAirportId) {
		AirportEntity to = airportService.findById(toAirportId).orElseThrow(CustomEntityNotFoundException::new);
		return flywayRepository.findByToAirport(to);
	}
}
