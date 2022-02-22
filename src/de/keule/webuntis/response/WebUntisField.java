package de.keule.webuntis.response;

import org.json.JSONObject;

public abstract class WebUntisField {
	protected String externalkey;
	protected JSONObject json;
	protected String longname;
	protected String name;
	protected int id;

	public WebUntisField(JSONObject json) {
		this.json = json;
		externalkey = json.optString("externalkey");
		longname = json.optString("longName");
		name = json.optString("name");
		id = json.optInt("id");
	}

	/* Getters */
	public String getExternalkey() {
		return externalkey;
	}

	public String getLongName() {
		return longname;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public JSONObject getJSON() {
		return json;
	}
}