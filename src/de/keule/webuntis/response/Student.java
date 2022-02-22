package de.keule.webuntis.response;

import org.json.JSONObject;

public class Student extends WebUntisField {
	private String foreName;
	private String gender;
	private int key;

	public Student(JSONObject json) {
		super(json);

		foreName = json.optString("foreName");
		gender = json.optString("gender");
		key = json.optInt("key");
	}

	public String getForeName() {
		return foreName;
	}

	public String getGender() {
		return gender;
	}

	public int getKey() {
		return key;
	}
}
