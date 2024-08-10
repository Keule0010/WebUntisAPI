package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONObject;

public class Schoolyears extends WebUntisDataWrapper<Schoolyear>{
	public Schoolyears(JSONObject json) {
		super(json, Schoolyear.class);
	}

	public List<Schoolyear> getSchoolyears() {
		return data;
	}
}
