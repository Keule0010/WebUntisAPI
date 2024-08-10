package de.keule.webuntis.response;

import org.json.JSONObject;

public class Subject extends ExtendedWebUntisField {
	private String alternateName;
	private boolean active;

	public Subject(JSONObject json) {
		super(json);

		alternateName = json.optString("alternateName");
		active = json.optBoolean("active");
	}

	public String getAlternateName() {
		return alternateName;
	}

	public boolean isActive() {
		return active;
	}
}
