package com.finartz.ticket.model.request;

import javax.validation.constraints.NotNull;

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.FlyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestAddFly {
	@NotNull
	private AirlineDTO airline;
	@NotNull
	private FlyDTO fly;
}
