package de.keule.webuntis.response;

import org.json.JSONObject;

public class Schoolyear extends WebUntisField {
	private int startDate;
	private int endDate;

	public Schoolyear(JSONObject json) {
		super(json);

		startDate = json.optInt("startDate");
		endDate = json.optInt("endDate");
	}

	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
		return endDate;
	}
}