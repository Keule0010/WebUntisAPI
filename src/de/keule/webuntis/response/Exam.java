package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Exam {
	private List<Integer> kl;
	private List<Integer> te;
	private List<Integer> st;
	private JSONObject json;
	private int startTime;
	private int endTime;
	private int subject;
	private int date;
	private int id;

	public Exam(JSONObject json) {
		this.json = json;

		kl = new ArrayList<>();
		te = new ArrayList<>();
		st = new ArrayList<>();

		startTime = json.optInt("startTime");
		endTime = json.optInt("endTime");
		subject = json.optInt("subject");
		date = json.optInt("date");
		id = json.optInt("id");

		JSONArray klJS = json.optJSONArray("classes");
		if (klJS != null) {
			for (int i = 0; i < klJS.length(); i++)
				kl.add(klJS.getInt(i));
		}

		JSONArray teJS = json.optJSONArray("teachers");
		if (teJS != null) {
			for (int i = 0; i < teJS.length(); i++)
				te.add(teJS.getInt(i));
		}

		JSONArray stJS = json.optJSONArray("students");
		if (stJS != null) {
			for (int i = 0; i < stJS.length(); i++)
				st.add(stJS.getInt(i));
		}

	}

	public List<Integer> getKlassen() {
		return kl;
	}

	public List<Integer> getStudents() {
		return st;
	}

	public List<Integer> getTeachers() {
		return te;
	}

	public JSONObject getJSON() {
		return json;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getSubject() {
		return subject;
	}

	public int getDate() {
		return date;
	}

	public int getId() {
		return id;
	}
}
