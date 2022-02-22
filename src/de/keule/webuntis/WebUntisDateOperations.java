package de.keule.webuntis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Locale;

public class WebUntisDateOperations {
	private static final SimpleDateFormat webUntisDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);

	private WebUntisDateOperations() {
	}

	public static int getStartDateFromWeek() {
		return getStartDateFromWeek(Calendar.getInstance(), 0);
	}

	public static int getStartDateFromWeek(Calendar week, int offset) {
		week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		week.add(Calendar.DAY_OF_MONTH, offset);
		return Integer.parseInt(webUntisDateFormat.format(week.getTime()));
	}

	public static int getDayOfWeekFromDate(int date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(webUntisDateFormat.parse(Integer.toString(date)));
			return c.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (ParseException e) {
			return -1;
		}
	}
	
	public static DayOfWeek getDayOfWeek(int date) {
		return DayOfWeek.of(getDayOfWeekFromDate(date));
	}
	
	public static int addDaysToDate(int startDate, int days) {
		if (days == 0)
			return startDate;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(webUntisDateFormat.parse(startDate + ""));
			c.add(Calendar.DATE, days);
			return Integer.parseInt(webUntisDateFormat.format(c.getTime()));
		} catch (ParseException e) {
			return startDate;
		}
	}

	public static String formatWebUntisDate(int date, String format) {
		return formatWebUntisDate(date, new SimpleDateFormat(format));
	}

	public static String formatWebUntisDate(int date, SimpleDateFormat format) {
		try {
			return webUntisDateFormat.format(format.parse(date + ""));
		} catch (ParseException e) {
			return date + "";
		}
	}

	public static int[] splitTime(int time) {
		int t[] = new int[2];
		t[1] = time % 100;
		t[0] = (time - t[1]) / 100;
		return t;
	}

	public static String formatWebUntisTime(int time, String format) {
		int[] timeSplit = splitTime(time);
		return String.format(format, timeSplit[0], timeSplit[1]);
	}
}