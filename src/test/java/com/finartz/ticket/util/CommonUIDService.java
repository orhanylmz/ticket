package com.finartz.ticket.util;

import java.util.UUID;

public class CommonUIDService {
	public static String getId() {
		return UUID.randomUUID().toString();
	}
}
