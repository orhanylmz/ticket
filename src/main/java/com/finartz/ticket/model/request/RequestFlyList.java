package com.finartz.ticket.model.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.FlywayDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFlyList {
	private AirlineDTO airline;
	@NotNull
	private FlywayDTO flyway;
	@NotNull
	private LocalDateTime start;
	@NotNull
	private LocalDateTime end;
}
