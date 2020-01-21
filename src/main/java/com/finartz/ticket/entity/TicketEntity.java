package com.finartz.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.finartz.ticket.enumeration.TicketStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "ticket")
public class TicketEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "ticket_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
	private Long id;

	@Column(name = "seat_number", unique = true)
	private String seatNumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fly_id")
	private FlyEntity fly;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private TicketStatus status;
}