package com.finartz.ticket.dto;

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
	private Long identityNumber;
	private String email;
	private String phone;
}