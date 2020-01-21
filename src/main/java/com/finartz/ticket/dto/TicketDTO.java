package com.finartz.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDTO extends BaseDTO {
	private Long id;
	private String seatNumber;
	private CustomerDTO customer;
	private FlyDTO fly;
}