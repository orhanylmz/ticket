package com.finartz.ticket.exception;

public class CustomRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -692472965667525463L;

	public CustomRuntimeException() {
		super();
	}

	public CustomRuntimeException(String message) {
		super(message);
	}
}
