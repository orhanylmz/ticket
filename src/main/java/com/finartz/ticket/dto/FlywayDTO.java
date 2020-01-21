package com.finartz.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FlywayDTO extends BaseDTO {
	private Long id;
	private AirportDTO fromAirport;
	private AirportDTO toAirport;
}