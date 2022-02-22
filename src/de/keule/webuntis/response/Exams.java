package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Exams extends WebUntisSimpleDataWrapper<Exam> {

	public Exams(JSONObject json) {
		super(json);

		JSONArray result = json.getJSONArray("result");
		for (int i = 0; i < result.length(); i++) {
			data.add(new Exam(result.getJSONObject(i)));
		}
	}

	public List<Exam> getExams() {
		return data;
	}
}
