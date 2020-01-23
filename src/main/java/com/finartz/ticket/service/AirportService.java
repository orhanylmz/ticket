package com.finartz.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.dto.AirportDTO;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.repository.AirportRepository;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirportService {
	private final AirportRepository airportRepository;
	private final CustomMapper mapper;

	public AirportEntity save(AirportEntity entity) {
		return airportRepository.save(entity);
	}
	
	public AirportEntity update(AirportDTO dto) {
		AirportEntity airport = findById(dto.getId()).orElseThrow(CustomEntityNotFoundException :: new);
		mapper.updateEntity(dto, airport);
		return save(airport);
	}

	public Optional<AirportEntity> findById(Long id) {
		return airportRepository.findById(id);
	}

	public Optional<AirportEntity> findByCode(String code) {
		return airportRepository.findByCode(code);
	}
	
	public Optional<AirportEntity> findByName(String name) {
		return airportRepository.findByName(name);
	}

	public List<AirportEntity> findByCountry(String country) {
		return airportRepository.findByCountry(country);
	}

	public List<AirportEntity> findByCountryAndProvince(String country, String province) {
		return airportRepository.findByCountryAndProvince(country, province);
	}
}
