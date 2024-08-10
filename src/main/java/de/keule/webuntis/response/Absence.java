package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Absence {
	private List<String> teacherIds;
	private String studentGroup;
	private String subjectId;
	private String studentId;
	private JSONObject json;
	private boolean checked;
	private boolean invalid;
	private int absentTime;
	private int startTime;
	private String reason;
	private String user;
	private int endTime;
	private int date;

	public Absence(JSONObject json) {
		this.json = json;
		this.teacherIds = new ArrayList<>();

		studentGroup = json.optString("studentGroup");
		subjectId = json.optString("subjectId");
		studentId = json.optString("studentId");
		reason = json.optString("absenceReason");
		absentTime = json.optInt("absentTime");
		checked = json.optBoolean("checked");
		invalid = json.optBoolean("invalid");
		startTime = json.optInt("startTime");
		endTime = json.optInt("endTime");
		user = json.optString("user");
		date = json.optInt("date");

		JSONArray teIds = json.optJSONArray("");
		if (teIds != null) {
			for (int i = 0; i < teIds.length(); i++)
				teacherIds.add(teIds.getString(i));
		}
	}

	public List<String> getTeacherIds() {
		return teacherIds;
	}

	public String getStudentGroup() {
		return studentGroup;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public String getStudentId() {
		return studentId;
	}

	public JSONObject getJSON() {
		return json;
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public int getAbsentTime() {
		return absentTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public String getReason() {
		return reason;
	}

	public String getUser() {
		return user;
	}

	public int getEndTime() {
		return endTime;
	}

	public int getDate() {
		return date;
	}
}
