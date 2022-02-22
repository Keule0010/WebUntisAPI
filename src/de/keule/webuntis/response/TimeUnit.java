package de.keule.webuntis.response;

import org.json.JSONObject;

public class TimeUnit {
	private JSONObject json;
	private int startTime;
	private int endTime;
	private String name;

	public TimeUnit(String name, int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
	}

	public TimeUnit(JSONObject json) {
		this.json = json;
		this.startTime = json.optInt("startTime");
		this.endTime = json.optInt("endTime");
		this.name = json.optString("name");
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}

	public JSONObject getJSON() {
		return json;
	}
}