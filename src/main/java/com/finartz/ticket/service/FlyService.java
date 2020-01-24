package com.finartz.ticket.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.repository.FlyRepository;
import com.finartz.ticket.util.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlyService {
	private final FlywayService flywayService;
	private final FlyRepository flyRepository;

	public FlyEntity save(FlyEntity entity) {
		return flyRepository.save(entity);
	}

	public FlyEntity incAndSave(FlyEntity entity) {
		entity.setOccupancyRate(entity.getOccupancyRate() + 1);
		return fixPrice(entity);
	}

	public FlyEntity decAndSave(FlyEntity entity) {
		entity.setOccupancyRate(entity.getOccupancyRate() - 1);
		return fixPrice(entity);
	}

	private FlyEntity fixPrice(FlyEntity entity) {
		entity.setPrice(entity.getOriginalPrice()
				.multiply(new BigDecimal(100 + (entity.getOccupancyRate() - entity.getOccupancyRate() % 10)))
				.divide(new BigDecimal(100)));
		return save(entity);
	}

	public Optional<FlyEntity> findById(Long id) {
		return flyRepository.findById(id);
	}

	public List<FlyEntity> findByFlightNumber(String flightNumber) {
		return flyRepository.findByFlightNumberAndFlyDateAfterOrderByFlyDate(flightNumber, DateUtil.now());
	}

	public List<FlyEntity> findByFlywayAndDateBetween(Long flywayId, LocalDateTime start, LocalDateTime end) {
		FlywayEntity flyway = flywayService.findById(flywayId).orElseThrow(CustomEntityNotFoundException::new);
		return flyRepository.findByFlywayAndFlyDateBetween(flyway, start, end);
	}
}
