package de.keule.webuntis.response;

import org.json.JSONObject;

public class Room extends ExtendedWebUntisField {
	private boolean active;
	private String building;

	public Room(JSONObject ro) {
		super(ro);
		active = json.optBoolean("active");
		building = json.optString("building");
	}

	public boolean isActive() {
		return active;
	}

	public String getBuilding() {
		return building;
	}
}
