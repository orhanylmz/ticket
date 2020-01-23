package com.finartz.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.AirlineEntity;

@Repository
public interface AirlineRepository extends JpaRepository<AirlineEntity, Long> {
}
