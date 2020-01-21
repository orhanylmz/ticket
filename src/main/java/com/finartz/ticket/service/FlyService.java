package com.finartz.ticket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.repository.FlyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlyService {
	private final FlyRepository flyRepository;

	public FlyEntity save(FlyEntity entity) {
		return flyRepository.save(entity);
	}

	public Optional<FlyEntity> findById(Long id) {
		return flyRepository.findById(id);
	}

	public List<FlyEntity> findByFlightNumber(String flightNumber) {
		return flyRepository.findByFlightNumber(flightNumber);
	}

	public List<FlyEntity> findByFlyway(FlywayEntity flyway) {
		return flyRepository.findByFlyway(flyway);
	}

	public List<FlyEntity> findByFlywayAndDateBetween(FlywayEntity flyway, LocalDateTime start, LocalDateTime end) {
		return flyRepository.findByFlywayAndDateBetween(flyway, start, end);
	}
}
