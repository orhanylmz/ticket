package com.finartz.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AirplaneDTO extends BaseDTO {
	private Long id;
	private String name;
	private Integer capacity;
	private AirlineDTO airline;
}