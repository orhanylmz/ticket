package com.finartz.ticket.exception;

public class AirplaneNotFoundException extends CustomRuntimeException {
	private static final long serialVersionUID = 1L;

	public AirplaneNotFoundException(String airline) {
		super(airline);
	}
}
