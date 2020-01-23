package com.finartz.ticket.dto;

import com.finartz.ticket.enumeration.TicketStatus;

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
	private CustomerDTO customer;
	private FlyDTO fly;
	private TicketStatus status = TicketStatus.ACTIVE;
}