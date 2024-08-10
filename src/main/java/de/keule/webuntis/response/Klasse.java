package de.keule.webuntis.response;

import org.json.JSONObject;

public class Klasse extends ExtendedWebUntisField {
	private boolean active;
	private int teacher1;
	private int teacher2;
	private int did;

	public Klasse(JSONObject json) {
		super(json);
		
		teacher1 = json.optInt("teacher1");
		teacher2 = json.optInt("teacher2");
		active = json.optBoolean("active");
		did = json.optInt("did");
	}

	public int getDid() {
		return did;
	}

	public int getTeacher1() {
		return teacher1;
	}

	public int getTeacher2() {
		return teacher2;
	}

	public boolean isActive() {
		return active;
	}
}