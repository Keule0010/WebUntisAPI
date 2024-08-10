package de.keule.webuntis.response;

import org.json.JSONObject;

public class Holiday extends WebUntisField {
	private int startDate;
	private int endDate;

	public Holiday(JSONObject json) {
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
