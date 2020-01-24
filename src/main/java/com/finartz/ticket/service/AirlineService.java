package com.finartz.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.repository.AirlineRepository;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirlineService {
	private final AirlineRepository airlineRepository;
	private final FlyService flyService;
	private final CustomMapper mapper;

	public AirlineEntity save(AirlineEntity entity) {
		return airlineRepository.save(entity);
	}

	public AirlineEntity update(AirlineDTO dto) {
		AirlineEntity airline = findById(dto.getId()).orElseThrow(CustomEntityNotFoundException::new);
		mapper.updateEntity(dto, airline);
		return save(airline);
	}

	public Optional<AirlineEntity> findById(Long id) {
		return airlineRepository.findById(id);
	}
	
	public Optional<AirlineEntity> findByName(String name) {
		return airlineRepository.findByName(name);
	}

	public FlyEntity addFly(AirlineDTO airlineDTO, FlyDTO flyDTO) {
		AirlineEntity airline = findById(airlineDTO.getId()).orElseThrow(CustomEntityNotFoundException::new);
		FlyEntity fly = mapper.mapDtoToEntity(flyDTO).setAirline(airline);
		return flyService.save(fly);
	}
}
