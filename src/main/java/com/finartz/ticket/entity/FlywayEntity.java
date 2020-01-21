package com.finartz.ticket.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "flyway")
public class FlywayEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "flyway_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flyway_id_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_airport_id")
	private AirportEntity fromAirport;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_airport_id")
	private AirportEntity toAirport;
}