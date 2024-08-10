package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Period {
	private List<Subject> subjects;
	private List<Teacher> teachers;
	private List<Klasse> klassen;
	private String activityType;
	private List<Room> rooms;
	private String substText;
	private String statflags;
	private String bkRemark;
	private JSONObject json;
	private int startTime;
	private String lstext;
	private String lstype;
	private String bkText;
	private int lsnumber;
	private int endTime;
	private String code;
	private String info;
	private String sg;
	private int date;
	private int id;

	public Period(JSONObject json) {
		this.json = json;
		id = json.optInt("id");
		date = json.optInt("date");
		sg = json.optString("sg");
		info = json.optString("info");
		code = json.optString("code", "");
		endTime = json.optInt("endTime");
		lsnumber = json.optInt("lsnumber");
		bkText = json.optString("bkText");
		lstype = json.optString("lstype", "ls");
		lstext = json.optString("lstext");
		startTime = json.optInt("startTime");
		bkRemark = json.optString("bkRemark");
		statflags = json.optString("statflags");
		substText = json.optString("substText");
		activityType = json.optString("activityType");
		
		JSONArray arr;
		rooms = new ArrayList<>();
		arr = json.optJSONArray("ro");
		if(arr != null)
			for (Object ro :arr) {
				rooms.add(new Room((JSONObject) ro));
			}
		
		klassen = new ArrayList<>();
		arr = json.optJSONArray("kl");
		if(arr != null)
			for (Object kl :arr) {
				klassen.add(new Klasse((JSONObject) kl));
			}
		
		subjects = new ArrayList<>();
		arr = json.optJSONArray("su");
		if(arr != null)
			for (Object su :arr) {
				subjects.add(new Subject((JSONObject) su));
			}
		
		teachers = new ArrayList<>();
		arr = json.optJSONArray("te");
		if(arr != null)
			for (Object te :arr) {
				teachers.add(new Teacher((JSONObject) te));
			}
	}
	
	/* Getters */
	public boolean isCancled() {
		return code != null && code.equalsIgnoreCase("cancelled");
	}
	
	public boolean isIrregular() {
		return code != null && code.equalsIgnoreCase("irregular");
	}
	
	public boolean isRegular() {
		return !isCancled() && !isIrregular();
	}
	
	public JSONObject getJSON() {
		return json;
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}

	public List<Klasse> getKlassen() {
		return klassen;
	}

	public String getActivityType() {
		return activityType;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public String getSubstText() {
		return substText;
	}

	public String getStatflags() {
		return statflags;
	}

	public String getBkRemark() {
		return bkRemark;
	}

	public int getStartTime() {
		return startTime;
	}

	public String getLstext() {
		return lstext;
	}

	public String getLstype() {
		return lstype;
	}

	public String getBkText() {
		return bkText;
	}

	public int getLsnumber() {
		return lsnumber;
	}

	public int getEndTime() {
		return endTime;
	}

	public String getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

	public String getStudyGroup() {
		return sg;
	}

	public int getDate() {
		return date;
	}

	public int getId() {
		return id;
	}
}