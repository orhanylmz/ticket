package com.finartz.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirplaneEntity;
import com.finartz.ticket.repository.AirplaneRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirplaneService {
	private final AirplaneRepository airplaneRepository;

	public AirplaneEntity save(AirplaneEntity entity) {
		return airplaneRepository.save(entity);
	}

	public Optional<AirplaneEntity> findById(Long id) {
		return airplaneRepository.findById(id);
	}

	public Optional<AirplaneEntity> findByName(String name) {
		return airplaneRepository.findByName(name);
	}

	public List<AirplaneEntity> findByAirline(AirlineEntity airline) {
		return airplaneRepository.findByAirline(airline);
	}
}
