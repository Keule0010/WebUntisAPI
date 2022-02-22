package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TimegridDay {
	private List<TimeUnit> timeUnits;
	private JSONObject json;
	private int day;
	
	public TimegridDay(JSONObject json) {
		this.json = json;
		this.timeUnits = new ArrayList<>();
		
		day = json.optInt("day");
		JSONArray timeUn = json.getJSONArray("timeUnits");
		for(int i = 0; i < timeUn.length(); i++)
			timeUnits.add(new TimeUnit(timeUn.getJSONObject(i)));
	}
	
	public List<TimeUnit> getTimeUnits(){
		return timeUnits;
	}
	
	public int getDay() {
		return day;
	}
	
	public JSONObject getJSON() {
		return json;
	}
}