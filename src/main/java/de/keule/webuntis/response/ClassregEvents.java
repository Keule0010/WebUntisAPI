package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClassregEvents extends WebUntisSimpleDataWrapper<ClassregEvent> {

	public ClassregEvents(JSONObject json) {
		super(json);

		JSONArray result = json.getJSONArray("result");
		for (int i = 0; i < result.length(); i++) {
			data.add(new ClassregEvent(result.getJSONObject(i)));
		}
	}

	public List<ClassregEvent> getClassregevents() {
		return data;
	}
}
