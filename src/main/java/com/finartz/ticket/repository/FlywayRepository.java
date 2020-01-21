package com.finartz.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.FlywayEntity;

@Repository
public interface FlywayRepository extends JpaRepository<FlywayEntity, Long> {
	Optional<FlywayEntity> findByFromAirportAndToAirport(AirportEntity fromAirport, AirportEntity toAirport);

	List<FlywayEntity> findByFromAirport(AirportEntity fromAirport);

	List<FlywayEntity> findByToAirport(AirportEntity toAirport);
}
