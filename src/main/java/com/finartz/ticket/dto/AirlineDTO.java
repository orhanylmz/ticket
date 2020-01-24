package com.finartz.ticket.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	@JsonInclude(value = Include.NON_NULL)
	private List<FlywayDTO> flywayList;
}