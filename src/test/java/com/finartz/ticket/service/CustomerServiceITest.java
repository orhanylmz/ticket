package com.finartz.ticket.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.service.CustomerService;
import com.finartz.ticket.util.CommonEntityService;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest
@EnableAsync
public class CustomerServiceITest {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CommonEntityService commonEntityService;

	@Test
	public void should_add_customer() {
		CustomerEntity customer = commonEntityService.generateCustomerEntity();
		customerService.save(customer);

		assertNotNull(customer);
		assertNotNull(customer.getId());
	}

	@Test
	public void should_add_and_find_by_id() {
		CustomerEntity customer = commonEntityService.generateCustomerEntity();
		customerService.save(customer);

		assertNotNull(customer);
		assertNotNull(customer.getId());

		CustomerEntity currentEntity = customerService.findById(customer.getId())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), customer.getId());
		assertEquals(currentEntity.getEmail(), customer.getEmail());
		assertEquals(currentEntity.getIdentityNumber(), customer.getIdentityNumber());
		assertEquals(currentEntity.getName(), customer.getName());
		assertEquals(currentEntity.getSurname(), customer.getSurname());
		assertEquals(currentEntity.getPhone(), customer.getPhone());
	}

	@Test
	public void should_add_and_find_by_email() {
		CustomerEntity customer = commonEntityService.generateCustomerEntity();
		customerService.save(customer);
		
		assertNotNull(customer);
		assertNotNull(customer.getId());
		
		CustomerEntity currentEntity = customerService.findByEmail(customer.getEmail())
				.orElseThrow(CustomEntityNotFoundException::new);
		
		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), customer.getId());
		assertEquals(currentEntity.getEmail(), customer.getEmail());
		assertEquals(currentEntity.getIdentityNumber(), customer.getIdentityNumber());
		assertEquals(currentEntity.getName(), customer.getName());
		assertEquals(currentEntity.getSurname(), customer.getSurname());
		assertEquals(currentEntity.getPhone(), customer.getPhone());
	}
	
	@Test
	public void should_add_and_find_by_identity_number() {
		CustomerEntity customer = commonEntityService.generateCustomerEntity();
		customerService.save(customer);

		assertNotNull(customer);
		assertNotNull(customer.getId());

		CustomerEntity currentEntity = customerService.findByIdentityNumber(customer.getIdentityNumber())
				.orElseThrow(CustomEntityNotFoundException::new);

		assertNotNull(currentEntity);
		assertNotNull(currentEntity.getId());
		assertEquals(currentEntity.getId(), customer.getId());
		assertEquals(currentEntity.getEmail(), customer.getEmail());
		assertEquals(currentEntity.getIdentityNumber(), customer.getIdentityNumber());
		assertEquals(currentEntity.getName(), customer.getName());
		assertEquals(currentEntity.getSurname(), customer.getSurname());
		assertEquals(currentEntity.getPhone(), customer.getPhone());
	}
}
