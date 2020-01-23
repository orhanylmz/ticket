package com.finartz.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FlyDTO extends BaseDTO {
	private Long id;
	private String flightNumber;
	private FlywayDTO flyway;
	private AirlineDTO airline;
	private LocalDateTime flyDate;
	private BigDecimal price;
	private Integer occupancyRate;
}