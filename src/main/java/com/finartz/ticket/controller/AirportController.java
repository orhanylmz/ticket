package com.finartz.ticket.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finartz.ticket.aop.LogExecutionTime;
import com.finartz.ticket.dto.AirportDTO;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.model.AirportListModel;
import com.finartz.ticket.service.AirportService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airport")
public class AirportController {
	private final AirportService airportService;
	private final CustomMapper mapper;

	@PostMapping("/v1/create")
	@LogExecutionTime
	public ResponseEntity<Long> create(@RequestBody AirportDTO airportDTO) {
		AirportEntity airport = airportService.save(mapper.mapDtoToEntity(airportDTO));
		return new ResponseEntity<>(airport.getId(), HttpStatus.CREATED);
	}

	@GetMapping("/v1/id/{id}")
	@LogExecutionTime
	public ResponseEntity<AirportDTO> id(@PathVariable Long id) {
		AirportEntity airport = airportService.findById(id).orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(airport), HttpStatus.OK);
	}

	@GetMapping("/v1/code/{code}")
	@LogExecutionTime
	public ResponseEntity<AirportDTO> code(@PathVariable String code) {
		AirportEntity airport = airportService.findByCode(code).orElseThrow(CustomEntityNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(airport), HttpStatus.OK);
	}

	@GetMapping("/v1/country/{country}")
	@LogExecutionTime
	public ResponseEntity<AirportListModel> country(@PathVariable String country) {
		List<AirportEntity> airportEntityList = airportService.findByCountry(country);
		List<AirportDTO> airportDTOList = airportEntityList.stream().map(entity -> mapper.mapEntityToDto(entity))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new AirportListModel().setAirportList(airportDTOList), HttpStatus.OK);
	}

	@GetMapping("/v1/country/{country}/province/{province}")
	@LogExecutionTime
	public ResponseEntity<AirportListModel> countryAndProvince(@PathVariable String country, @PathVariable String province) {
		List<AirportEntity> airportEntityList = airportService.findByCountryAndProvince(country, province);
		List<AirportDTO> airportDTOList = airportEntityList.stream().map(entity -> mapper.mapEntityToDto(entity))
				.collect(Collectors.toList());
		return new ResponseEntity<>(new AirportListModel().setAirportList(airportDTOList), HttpStatus.OK);
	}
}