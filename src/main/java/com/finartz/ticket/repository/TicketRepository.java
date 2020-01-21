package com.finartz.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.TicketEntity;
import com.finartz.ticket.enumeration.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
	Optional<TicketEntity> findBySeatNumber(String seatNumber);
	
	Optional<TicketEntity> findByCustomer(CustomerEntity customer);
	
	List<TicketEntity> findByFly(FlyEntity fly);
	
	List<TicketEntity> findByFlyAndStatus(FlyEntity fly, TicketStatus status);
}
