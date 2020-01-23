package com.finartz.ticket.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "airline", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class AirlineEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "airline_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airline_id_seq")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "flyway", joinColumns = @JoinColumn(name = "airline_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "flyway_id", referencedColumnName = "id"))
	private List<FlywayEntity> flywayList;
}