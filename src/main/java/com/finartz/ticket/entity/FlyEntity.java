package com.finartz.ticket.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
@Table(name = "fly")
public class FlyEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "fly_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fly_id_seq")
	private Long id;

	@Column(name = "flight_number")
	private String flightNumber;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "flyway_id")
	private FlywayEntity flyway;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "airplane_id")
	private AirplaneEntity airplane;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "price")
	private BigDecimal price;
}