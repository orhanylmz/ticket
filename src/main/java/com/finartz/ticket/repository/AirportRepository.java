package com.finartz.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.AirportEntity;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {
	Optional<AirportEntity> findByName(String name);
	
	List<AirportEntity> findByCountry(String country);
	
	List<AirportEntity> findByCountryAndProvince(String country, String province);
}
