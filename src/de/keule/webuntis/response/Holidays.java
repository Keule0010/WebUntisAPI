package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Holidays extends WebUntisDataWrapper<Holiday> {

	public Holidays(JSONObject json) {
		super(json, Holiday.class);
	}

	public void sortByStartDate() {
		data.sort(Comparator.comparing(Holiday::getStartDate));
	}

	public void sortByEndDate() {
		data.sort(Comparator.comparing(Holiday::getEndDate));
	}

	public Holiday getHoliday(int i) {
		return data.get(i);
	}

	public List<Holiday> getHolidays() {
		return data;
	}
}
