package de.keule.webuntis.response;

import org.json.JSONObject;

public class Reschedule {
	private JSONObject json;
	private int startTime;
	private int endTime;
	private int date;

	public Reschedule(JSONObject json) {
		this.json = json;
		
		if(json == null)
			return;
		
		startTime = json.optInt("startTime");
		endTime = json.optInt("endTime");
		date = json.optInt("date");
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getDate() {
		return date;
	}

	public JSONObject getJSON() {
		return json;
	}
}
