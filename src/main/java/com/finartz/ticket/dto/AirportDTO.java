package com.finartz.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AirportDTO extends BaseDTO {
	private Long id;
	private String code;
	private String name;
	private String country;
	private String province;
}