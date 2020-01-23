package com.finartz.ticket.exception;

import com.finartz.ticket.entity.CheckinEntity;

public class CheckinAlreadyExistException extends CustomRuntimeException {
	private static final long serialVersionUID = 1L;

	public CheckinAlreadyExistException(CheckinEntity checkin) {
		super(checkin.getId() + "");
	}
}
