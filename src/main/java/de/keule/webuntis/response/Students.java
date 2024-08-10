package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Students extends WebUntisDataWrapper<Student> {

	public Students(JSONObject json) {
		super(json, Student.class);
	}

	public void sortByKey() {
		data.sort(Comparator.comparing(Student::getKey));
	}

	public void sortByGender() {
		data.sort(Comparator.comparing(Student::getGender));
	}

	public Student getStudent(int i) {
		return data.get(i);
	}

	public List<Student> getStudents() {
		return data;
	}
}
