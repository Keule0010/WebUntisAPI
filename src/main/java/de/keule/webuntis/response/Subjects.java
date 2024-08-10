package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

public class Subjects extends WebUntisDataWrapper<Subject> {

	public Subjects(JSONObject json) {
		super(json, Subject.class);
	}

	public void sortAlternateName() {
		data.sort(Comparator.comparing(Subject::getAlternateName));
	}

	public Subject getSubject(int i) {
		return data.get(i);
	}

	public List<Subject> getSubjects() {
		return data;
	}
}
