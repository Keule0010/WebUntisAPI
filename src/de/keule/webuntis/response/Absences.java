package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Absences extends WebUntisSimpleDataWrapper<Absence>{

	public Absences(JSONObject json) {
		super(json);
		
		JSONArray result = json.getJSONObject("result").getJSONArray("periodsWithAbsences");
		for (int i = 0; i < result.length(); i++) {
			data.add(new Absence(result.getJSONObject(i)));
		}
	}

	public List<Absence> getAbsences() {
		return data;
	}
}
