package com.finartz.ticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartz.ticket.entity.CheckinEntity;
import com.finartz.ticket.entity.TicketEntity;

@Repository
public interface CheckinRepository extends JpaRepository<CheckinEntity, Long> {
	Optional<CheckinEntity> findByTicket(TicketEntity ticket);
}
