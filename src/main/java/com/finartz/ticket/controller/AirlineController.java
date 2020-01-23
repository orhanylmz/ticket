package com.finartz.ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finartz.ticket.aop.LogExecutionTime;
import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.model.request.RequestAddFly;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airline")
public class AirlineController {
	private final AirlineService airlineService;
	private final CustomMapper mapper;

	@PutMapping("/v1/create")
	@LogExecutionTime
	public ResponseEntity<AirlineDTO> create(@RequestBody AirlineDTO airlineDTO) {
		AirlineEntity airline = airlineService.save(mapper.mapDtoToEntity(airlineDTO));
		return new ResponseEntity<>(mapper.mapEntityToDto(airline), HttpStatus.CREATED);
	}

	@PostMapping("/v1/update")
	@LogExecutionTime
	public ResponseEntity<AirlineDTO> update(@RequestBody AirlineDTO airlineDTO) {
		AirlineEntity airline = airlineService.update(airlineDTO);
		return new ResponseEntity<>(mapper.mapEntityToDto(airline), HttpStatus.OK);
	}

	@GetMapping("/v1/id/{airlineId}")
	@LogExecutionTime
	public ResponseEntity<AirlineDTO> id(@PathVariable Long airlineId) {
		AirlineEntity airline = airlineService.findById(airlineId).orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(airline), HttpStatus.OK);
	}

	@PostMapping("/v1/addFly")
	@LogExecutionTime
	public ResponseEntity<FlyDTO> addFly(@RequestBody RequestAddFly request) {
		FlyEntity fly = airlineService.addFly(request.getAirline(), request.getFly());
		return new ResponseEntity<>(mapper.mapEntityToDto(fly), HttpStatus.CREATED);
	}
}