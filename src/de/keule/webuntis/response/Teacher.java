package de.keule.webuntis.response;

import org.json.JSONObject;

public class Teacher extends ExtendedWebUntisField {
	private String foreName;

	public Teacher(JSONObject json) {
		super(json);

		foreName = json.optString("foreName");
	}

	public String getForeName() {
		return foreName;
	}
}