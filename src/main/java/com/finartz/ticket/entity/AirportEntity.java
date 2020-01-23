package com.finartz.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "airport")
public class AirportEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "airport_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airport_id_seq")
	private Long id;
	
	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "country")
	private String country;
	
	@Column(name = "province")
	private String province;
}