package com.finartz.ticket.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerDTO extends BaseDTO {
	private Long id;
	private String name;
	private String surname;
	@NotNull
	private Long identityNumber;
	private String email;
	@NotNull
	private String phone;
}