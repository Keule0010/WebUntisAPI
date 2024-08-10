package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Teachers extends WebUntisDataWrapper<Teacher> {

	public Teachers(JSONObject json) {
		super(json, Teacher.class);
	}

	public void sortByForeName() {
		data.sort(Comparator.comparing(Teacher::getForeName));
	}

	public Teacher getTeacher(int i) {
		return data.get(i);
	}

	public List<Teacher> getTeachers() {
		return data;
	}
}
