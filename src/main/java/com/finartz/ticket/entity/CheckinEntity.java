package com.finartz.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "checkin")
public class CheckinEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "checkin_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkin_id_seq")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ticket_id")
	private TicketEntity ticket;
}