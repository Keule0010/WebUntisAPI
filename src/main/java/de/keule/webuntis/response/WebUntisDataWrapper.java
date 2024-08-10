package de.keule.webuntis.response;

import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class WebUntisDataWrapper<T extends WebUntisField> extends WebUntisSimpleDataWrapper<T> {

	public WebUntisDataWrapper(JSONObject json, Class<T> s) {
		super(json);

		JSONArray result = json.getJSONArray("result");
		for (int i = 0; i < result.length(); i++)
			try {
				data.add((T) s.getDeclaredConstructor(JSONObject.class).newInstance((result.getJSONObject(i))));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void sortByName() {
		data.sort(Comparator.comparing(WebUntisField::getName));
	}

	public void sortByLongName() {
		data.sort(Comparator.comparing(WebUntisField::getLongName));
	}

	public void sortById() {
		data.sort(Comparator.comparing(WebUntisField::getId));
	}
}
