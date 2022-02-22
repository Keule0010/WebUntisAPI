package de.keule.webuntis.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class WebUntisSimpleDataWrapper<T> {
	protected List<T> data;
	private JSONObject json;
	private String requestId;

	public WebUntisSimpleDataWrapper(JSONObject json) {
		this.json = json;
		this.data = new ArrayList<>();
		this.requestId = json.optString("id");
	}

	public String getRequestId() {
		return requestId;
	}

	public JSONObject getJSON() {
		return json;
	}
}
