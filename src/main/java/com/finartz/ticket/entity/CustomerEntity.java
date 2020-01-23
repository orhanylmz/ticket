package com.finartz.ticket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "customer", uniqueConstraints = { @UniqueConstraint(columnNames = { "identity_number" }) })
public class CustomerEntity extends BaseEntity {
	@Id
	@SequenceGenerator(name = "customer_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "identity_number")
	private Long identityNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
}