package com.finartz.ticket.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finartz.ticket.aop.LogExecutionTime;
import com.finartz.ticket.dto.FlywayDTO;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.FlywayService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flyway")
public class FlywayController {
	private final FlywayService flywayService;
	private final CustomMapper mapper;

	@PutMapping("/v1/create")
	@LogExecutionTime
	public ResponseEntity<Long> create(@RequestBody FlywayDTO flywayDTO) {
		FlywayEntity flyway = flywayService.save(mapper.mapDtoToEntity(flywayDTO));
		return new ResponseEntity<>(flyway.getId(), HttpStatus.CREATED);
	}

	@PutMapping("/v1/update")
	@LogExecutionTime
	public ResponseEntity<FlywayDTO> update(@RequestBody FlywayDTO flywayDTO) {
		FlywayEntity flyway = flywayService.update(flywayDTO);
		return new ResponseEntity<>(mapper.mapEntityToDto(flyway), HttpStatus.OK);
	}

	@GetMapping("/v1/id/{id}")
	@LogExecutionTime
	public ResponseEntity<FlywayDTO> id(@PathVariable Long id) {
		FlywayEntity flyway = flywayService.findById(id).orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(flyway), HttpStatus.OK);
	}

	@GetMapping("/v1/from/{fromAirportId}")
	@LogExecutionTime
	public ResponseEntity<List<FlywayDTO>> from(@PathVariable Long fromAirportId) {
		List<FlywayEntity> flywayEntityList = flywayService.findByFromAirport(fromAirportId);
		List<FlywayDTO> flywayDTOList = flywayEntityList.stream().map(entity -> mapper.mapEntityToDto(entity))
				.collect(Collectors.toList());
		return new ResponseEntity<>(flywayDTOList, HttpStatus.OK);
	}

	@GetMapping("/v1/to/{toAirportId}")
	@LogExecutionTime
	public ResponseEntity<List<FlywayDTO>> to(@PathVariable Long toAirportId) {
		List<FlywayEntity> flywayEntityList = flywayService.findByToAirport(toAirportId);
		List<FlywayDTO> flywayDTOList = flywayEntityList.stream().map(entity -> mapper.mapEntityToDto(entity))
				.collect(Collectors.toList());
		return new ResponseEntity<>(flywayDTOList, HttpStatus.OK);
	}

	@GetMapping("/v1/from/{fromAirportId}/to/{toAirportId}")
	@LogExecutionTime
	public ResponseEntity<FlywayDTO> to(@PathVariable Long fromAirportId, @PathVariable Long toAirportId) {
		FlywayEntity flyway = flywayService.findByFromAirportAndToAirport(fromAirportId, toAirportId)
				.orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(flyway), HttpStatus.OK);
	}
}
