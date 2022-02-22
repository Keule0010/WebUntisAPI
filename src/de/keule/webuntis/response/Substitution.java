package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Substitution {
	private Reschedule rescheduled;
	private String orgexternalkey;
	private List<Teacher> te;
	private List<Subject> su;
	private List<Klasse> kl;
	private JSONObject json;
	private String orgname;
	private List<Room> ro;
	private int startTime;
	private String type;
	private String text;
	private int endTime;
	private int date;
	private int lsid;

	public Substitution(JSONObject json) {
		this.json = json;

		te = new ArrayList<>();
		su = new ArrayList<>();
		kl = new ArrayList<>();
		ro = new ArrayList<>();

		rescheduled = new Reschedule(json.optJSONObject("reschedule"));
		orgexternalkey = json.optString("orgexternalkey");
		startTime = json.optInt("startTime");
		orgname = json.optString("orgname");
		endTime = json.optInt("endTime");
		type = json.optString("type");
		text = json.optString("txt");
		date = json.optInt("date");
		lsid = json.optInt("lsid");

		JSONArray teJS = json.optJSONArray("te");
		if (teJS != null) {
			for (int i = 0; i < teJS.length(); i++)
				te.add(new Teacher(teJS.getJSONObject(i)));
		}

		JSONArray suJS = json.optJSONArray("su");
		if (suJS != null) {
			for (int i = 0; i < suJS.length(); i++)
				su.add(new Subject(suJS.getJSONObject(i)));
		}

		JSONArray klJS = json.optJSONArray("kl");
		if (klJS != null) {
			for (int i = 0; i < klJS.length(); i++)
				kl.add(new Klasse(klJS.getJSONObject(i)));
		}

		JSONArray roJS = json.optJSONArray("ro");
		if (roJS != null) {
			for (int i = 0; i < roJS.length(); i++)
				ro.add(new Room(roJS.getJSONObject(i)));
		}
	}

	public Reschedule getRescheduled() {
		return rescheduled;
	}

	public String getOrgexternalkey() {
		return orgexternalkey;
	}

	public String getOrgname() {
		return orgname;
	}

	public String getSubtitutionText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getLsid() {
		return lsid;
	}

	public int getDate() {
		return date;
	}

	public List<Teacher> getTeachers() {
		return te;
	}

	public List<Subject> getSubjects() {
		return su;
	}

	public List<Klasse> getKlassen() {
		return kl;
	}

	public List<Room> getRooms() {
		return ro;
	}

	public JSONObject getJSON() {
		return json;
	}
}
