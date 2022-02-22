package de.keule.webuntis.response;

import org.json.JSONObject;

public class StatusDataField {
	private String foreColor;
	private String backColor;
	private JSONObject json;
	private String name;

	public StatusDataField(String name, JSONObject json) {
		this.json = json;
		this.name = name;
		foreColor = json.optString("foreColor");
		backColor = json.optString("backColor");
	}

	public String getForeColor() {
		return foreColor;
	}

	public String getBackColor() {
		return backColor;
	}

	public String getName() {
		return name;
	}

	public JSONObject getJSON() {
		return json;
	}
}
