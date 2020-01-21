package com.finartz.ticket.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private final static String outputDateFormat = "yyyy-MM-dd HH:mm:ss";
	private final static String inputDateFormat = "yyyy-M-d H:mm:ss";
	private final static String TIMEZONE = "Europe/Istanbul";

	public static Date yesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(-1, Calendar.DAY_OF_YEAR);
		return calendar.getTime();
	}

	public static Date lastWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(-1, Calendar.WEEK_OF_YEAR);
		return calendar.getTime();
	}

	public static LocalDateTime now() {
		return LocalDateTime.now(getZoneId());
	}

	public static boolean isExpired(LocalDateTime nextTime){
		return now().isAfter(nextTime);
	}


	public static LocalDate nowDate() {
		return LocalDate.now(getZoneId());
	}

	public static LocalTime nowTime() {
		return LocalTime.now(getZoneId());
	}

	private static ZoneId getZoneId() {
		return ZoneId.of(TIMEZONE);
	}

	public static String formatLocalDateTimeToDateString(LocalDateTime date) {
		if (date == null) {
			return null;
		}
		return date.format(DateTimeFormatter.ofPattern(outputDateFormat));
	}

	public static LocalDateTime formatDateStringToLocalDateTime(String dateString) {
		return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(inputDateFormat));
	}

	public static Long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	public static LocalDateTime generateDateFromTimestamp(String timestamp) {
		Timestamp ts = new Timestamp(Long.parseLong(timestamp));
		return ts.toLocalDateTime();
	}
}
