package de.keule.webuntis.response;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Substitutions extends WebUntisSimpleDataWrapper<Substitution> {

	public Substitutions(JSONObject json) {
		super(json);

		JSONArray result = json.getJSONArray("result");
		for (int i = 0; i < result.length(); i++) {
			data.add(new Substitution(result.getJSONObject(i)));
		}
	}

	public List<Substitution> getSubstitions() {
		return data;
	}
}
