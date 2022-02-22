package de.keule.webuntis.response;

import org.json.JSONObject;

public abstract class ExtendedWebUntisField extends WebUntisField {
	private String foreColor;
	private String backColor;

	public ExtendedWebUntisField(JSONObject json) {
		super(json);

		foreColor = json.optString("foreColor");
		backColor = json.optString("backColor");
	}

	public String getForeColor() {
		return foreColor;
	}

	public String getBackColor() {
		return backColor;
	}
}
