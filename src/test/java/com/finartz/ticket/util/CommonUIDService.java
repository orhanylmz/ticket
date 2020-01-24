package com.finartz.ticket.util;

import java.util.UUID;

public class CommonUIDService {
	public static synchronized String getId() {
		return UUID.randomUUID().toString();
	}
}
