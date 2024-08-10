package de.keule.webuntis.response;

import java.time.DayOfWeek;
import java.util.ArrayList;

import de.keule.webuntis.WebUntisDateOperations;

public class TimetableDay extends BaseTimetable{
	private DayOfWeek dayOfWeek;
	private int date;

	public TimetableDay(ArrayList<Period> res, int date) {
		this.date = date;
		this.periods = res;
		this.dayOfWeek = WebUntisDateOperations.getDayOfWeek(date);
	}

	public int getDate() {
		return date;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
}