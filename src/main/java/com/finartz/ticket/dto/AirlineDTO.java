package com.finartz.ticket.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AirlineDTO extends BaseDTO {
	private Long id;
	private String name;
	private List<FlywayDTO> flaywayList;
}