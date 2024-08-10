package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Timegrid {
	private Map<Integer, TimegridDay> timegrid;
	private List<Integer> days;
	private JSONObject json;

	public Timegrid(JSONObject json) {
		this.json = json;
		timegrid = new HashMap<>();
		days = new ArrayList<>();
		
		JSONArray result = json.getJSONArray("result");
		for(int i = 0; i < result.length(); i++) {
			TimegridDay t = new TimegridDay(result.getJSONObject(i));	
			timegrid.put(t.getDay(), t);
			days.add(t.getDay());
		}
	}
	
	public TimegridDay getTimegridDay(int day) {
		return timegrid.get(day);
	}
	
	public Map<Integer, TimegridDay> getTimegrid(){
		return timegrid;
	}
	
	public List<Integer> getDays(){
		return days;
	}
	
	public JSONObject getJSON() {
		return json;
	}
}
