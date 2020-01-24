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
import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.model.FlyListModel;
import com.finartz.ticket.model.RequestFlyList;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.service.FlyService;
import com.finartz.ticket.util.CustomMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fly")
public class FlyController {
	private final AirlineService airlineService;
	private final FlyService flyService;
	private final CustomMapper mapper;

	@PostMapping("/v1/create")
	@LogExecutionTime
	public ResponseEntity<Long> create(@RequestBody FlyDTO flyDTO) {
		AirlineEntity airline = airlineService.findById(flyDTO.getAirline().getId())
				.orElseThrow(CustomEntityNotFoundException::new);
		FlyEntity fly = flyService.save(mapper.mapDtoToEntity(flyDTO).setAirline(airline));
		return new ResponseEntity<>(fly.getId(), HttpStatus.CREATED);
	}

	@GetMapping("/v1/id/{flyId}")
	@LogExecutionTime
	public ResponseEntity<FlyDTO> id(@PathVariable Long flyId) {
		FlyEntity fly = flyService.findById(flyId).orElseThrow(FlyNotFoundException::new);
		return new ResponseEntity<>(mapper.mapEntityToDto(fly), HttpStatus.OK);
	}

	@GetMapping("/v1/flightNumber/{flightNumber}")
	@LogExecutionTime
	public ResponseEntity<FlyListModel> flightNumber(@PathVariable String flightNumber) {
		List<FlyEntity> flyList = flyService.findByFlightNumber(flightNumber);
		List<FlyDTO> flyDTOList = flyList.stream().map(fly -> mapper.mapEntityToDto(fly)).collect(Collectors.toList());
		return new ResponseEntity<>(new FlyListModel().setFlyList(flyDTOList), HttpStatus.OK);
	}

	@PostMapping("/v1/flyList")
	@LogExecutionTime
	public ResponseEntity<FlyListModel> flyList(@RequestBody RequestFlyList request) {
		List<FlyEntity> flyList = flyService.findByFlywayAndDateBetween(request.getFlyway().getId(), request.getStart(),
				request.getEnd());
		if (!ObjectUtils.isEmpty(request.getAirline())) {
			flyList.stream().filter(fly -> fly.getAirline().getId().compareTo(request.getAirline().getId()) == 0)
					.collect(Collectors.toList());
		}
		List<FlyDTO> flyDTOList = flyList.stream().map(fly -> mapper.mapEntityToDto(fly)).collect(Collectors.toList());
		return new ResponseEntity<>(new FlyListModel().setFlyList(flyDTOList), HttpStatus.OK);
	}
}
