package de.keule.webuntis.response;

import org.json.JSONObject;

public class ClassregEvent {
	private String studentid;
	private String surename;
	private String forename;
	private JSONObject json;
	private int categoryId;
	private String subject;
	private String reason;
	private String text;
	private int date;

	public ClassregEvent(JSONObject json) {
		this.json = json;

		studentid = json.optString("studentid");
		categoryId = json.optInt("categoryId");
		surename = json.optString("surname");
		forename = json.optString("forname");
		subject = json.optString("subject");
		reason = json.optString("reason");
		text = json.optString("text");
		date = json.optInt("date");
	}

	public String getStudentid() {
		return studentid;
	}

	public String getSurename() {
		return surename;
	}

	public String getForename() {
		return forename;
	}

	public JSONObject getJSON() {
		return json;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getSubject() {
		return subject;
	}

	public String getReason() {
		return reason;
	}

	public String getText() {
		return text;
	}

	public int getDate() {
		return date;
	}

}
