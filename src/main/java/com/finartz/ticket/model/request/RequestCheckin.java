package com.finartz.ticket.model.request;

import javax.validation.constraints.NotNull;

import com.finartz.ticket.dto.CustomerDTO;
import com.finartz.ticket.dto.FlyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCheckin {
	@NotNull
	private CustomerDTO customer;
	@NotNull
	private FlyDTO fly;
}
