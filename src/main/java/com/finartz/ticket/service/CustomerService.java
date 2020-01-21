package com.finartz.ticket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerEntity save(CustomerEntity entity) {
		return customerRepository.save(entity);
	}

	public Optional<CustomerEntity> findById(Long id) {
		return customerRepository.findById(id);
	}

	public Optional<CustomerEntity> findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public Optional<CustomerEntity> findByIdentityNumber(Long identityNumber) {
		return customerRepository.findByIdentityNumber(identityNumber);
	}
}
