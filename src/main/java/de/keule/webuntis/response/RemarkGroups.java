package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONObject;

public class RemarkGroups extends WebUntisDataWrapper<RemarkGroup>{

	public RemarkGroups(JSONObject json) {
		super(json, RemarkGroup.class);
	}

	public List<RemarkGroup> getRemarkGroups() {
		return data;
	}
}
