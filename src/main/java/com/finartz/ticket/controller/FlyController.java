package com.finartz.ticket.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finartz.ticket.aop.LogExecutionTime;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.model.request.RequestFlyList;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fly")
public class FlyController {
	private final FlyService flyService;
	private final CustomMapper mapper;

	@GetMapping("/v1/flightNumber/{flightNumber}")
	@LogExecutionTime
	public ResponseEntity<List<FlyDTO>> flightNumber(@PathVariable String flightNumber) {
		List<FlyEntity> flyList = flyService.findByFlightNumber(flightNumber);
		List<FlyDTO> flyDTOList = flyList.stream().map(fly -> mapper.mapEntityToDto(fly)).collect(Collectors.toList());
		return new ResponseEntity<>(flyDTOList, HttpStatus.OK);
	}

	@PostMapping("/v1/flyway")
	@LogExecutionTime
	public ResponseEntity<List<FlyDTO>> flyway(@RequestBody final RequestFlyList request) {
		List<FlyEntity> flyList = flyService.findByFlywayAndDateBetween(request.getFlyway().getId(), request.getStart(),
				request.getEnd());
		if (!ObjectUtils.isEmpty(request.getAirline())) {
			flyList.stream().filter(fly -> fly.getAirline().getId().compareTo(request.getAirline().getId()) == 0)
					.collect(Collectors.toList());
		}
		List<FlyDTO> flyDTOList = flyList.stream().map(fly -> mapper.mapEntityToDto(fly)).collect(Collectors.toList());
		return new ResponseEntity<>(flyDTOList, HttpStatus.OK);
	}
}
