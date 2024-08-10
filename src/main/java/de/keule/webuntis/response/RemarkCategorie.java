package de.keule.webuntis.response;

import org.json.JSONObject;

public class RemarkCategorie extends WebUntisField{
	private int groupId;
	
	public RemarkCategorie(JSONObject json) {
		super(json);
		
		groupId = json.optInt("groupId");
	}

	public int getGroupId() {
		return groupId;
	}
}
