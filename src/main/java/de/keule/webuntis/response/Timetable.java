package de.keule.webuntis.response;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import de.keule.webuntis.WebUntisDateOperations;

public class Timetable extends BaseTimetable {
	private Map<Integer, TimetableDay> days;
	private List<Integer> dates;
	private JSONObject json;

	public Timetable(JSONObject json) {
		this.json = json;
		periods = new ArrayList<>();
		dates = new ArrayList<>();
		days = new HashMap<>();

		JSONArray arr = json.getJSONArray("result");
		for (int i = 0; i < arr.length(); i++)
			periods.add(new Period(arr.getJSONObject(i)));

		for (Period p : periods)
			if (!dates.contains(p.getDate()))
				dates.add(p.getDate());
	}

	public TimetableDay getTimetableForDate(int date) {
		addTimetableForDate(date);

		if (days.containsKey(date))
			return days.get(date);
		return null;
	}

	public List<TimetableDay> getTimetablesForDay(DayOfWeek day) {
		ArrayList<TimetableDay> returnDays = new ArrayList<>();

		for (int date : dates)
			if (WebUntisDateOperations.getDayOfWeek(date) == day) {
				addTimetableForDate(date);
				if (days.containsKey(date))
					returnDays.add(days.get(date));
			}
		return returnDays;
	}

	private void addTimetableForDate(int date) {
		if (days.containsKey(date))
			return;

		ArrayList<Period> res = new ArrayList<>();
		for (Period p : periods)
			if (p.getDate() == date)
				res.add(p);

		days.put(date, new TimetableDay(res, date));
	}

	public List<Integer> getDates() {
		return dates;
	}

	public JSONObject getJSON() {
		return json;
	}
}