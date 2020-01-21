package com.finartz.ticket.entity;

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
@Table(name = "airplane")
public class AirplaneEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "airplane_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airplane_id_seq")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "airline_id")
	private AirlineEntity airline;
}