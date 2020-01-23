package com.finartz.ticket.model.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestFlyList {
	private Long airline;
	@NotNull
	private Long flyway;
	@NotNull
	private LocalDateTime start;
	@NotNull
	private LocalDateTime end;
}
