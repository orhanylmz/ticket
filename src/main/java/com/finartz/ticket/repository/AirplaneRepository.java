package com.finartz.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirplaneEntity;

@Repository
public interface AirplaneRepository extends JpaRepository<AirplaneEntity, Long> {
	Optional<AirplaneEntity> findByName(String name);

	List<AirplaneEntity> findByAirline(AirlineEntity airline);
}
