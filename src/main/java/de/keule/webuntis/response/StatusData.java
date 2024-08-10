package de.keule.webuntis.response;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatusData {
	public static final String[] LSTYPE_KEYS = { "ls", "oh", "sb", "bs", "ex" };
	public static final String[] CODE_KEYS = { "cancelled", "irregular" };
	private StatusDataField[] lstypes;
	private StatusDataField[] codes;
	private JSONObject json;

	public StatusData(JSONObject json) {
		this.json = json;
		lstypes = new StatusDataField[5];
		codes = new StatusDataField[2];

		JSONObject result = json.getJSONObject("result");
		JSONArray codesJS = result.getJSONArray("codes");
		for (int i = 0; i < codesJS.length(); i++)
			codes[i] = new StatusDataField(CODE_KEYS[i], codesJS.getJSONObject(i).getJSONObject(CODE_KEYS[i]));

		JSONArray lstypesJS = result.getJSONArray("lstypes");
		for (int i = 0; i < lstypesJS.length(); i++)
			lstypes[i] = new StatusDataField(LSTYPE_KEYS[i], lstypesJS.getJSONObject(i).getJSONObject(LSTYPE_KEYS[i]));
	}

	public StatusDataField getCode(String name, boolean ignoreCase) {
		for (StatusDataField code : codes) {
			if (ignoreCase && code.getName().equalsIgnoreCase(name))
				return code;
			else if (!ignoreCase && code.getName().equals(name))
				return code;
		}
		return null;
	}

	public StatusDataField getLstype(String name, boolean ignoreCase) {
		for (StatusDataField lstype : lstypes) {
			if (ignoreCase && lstype.getName().equalsIgnoreCase(name))
				return lstype;
			else if (!ignoreCase && lstype.getName().equals(name))
				return lstype;
		}
		return null;
	}

	public StatusDataField[] getCodes() {
		return codes;
	}

	public StatusDataField[] getLstypes() {
		return lstypes;
	}

	public JSONObject getJSON() {
		return json;
	}
}