package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Schools extends WebUntisSimpleDataWrapper<School> {

	public Schools(JSONObject json) {
		super(json);

		JSONArray schoolsJSON = json.getJSONObject("result").getJSONArray("schools");
		for (int i = 0; i < schoolsJSON.length(); i++)
			data.add(new School(schoolsJSON.getJSONObject(i)));
	}

	public void sortByServer() {
		data.sort(Comparator.comparing(School::getServer));
	}

	public void sortSchoolId() {
		data.sort(Comparator.comparing(School::getSchoolId));
	}

	public void sortLoginName() {
		data.sort(Comparator.comparing(School::getLoginName));
	}

	public void sortByDisplayName() {
		data.sort(Comparator.comparing(School::getDisplayName));
	}

	public List<School> getSchools() {
		return data;
	}
}
