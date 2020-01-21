package com.finartz.ticket.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;

@Repository
public interface FlyRepository extends JpaRepository<FlyEntity, Long> {
	List<FlyEntity> findByFlightNumber(String flightNumber);

	List<FlyEntity> findByFlyway(FlywayEntity flyway);

	List<FlyEntity> findByFlywayAndDateBetween(FlywayEntity flyway, LocalDateTime start, LocalDateTime end);
}
