package de.keule.webuntis;

import org.json.JSONObject;

public class WebUntisResponse {
	private JSONObject response;
	private boolean hasError;

	public WebUntisResponse(JSONObject response) {
		this.response = response;
		this.hasError = hasError(response);
	}

	public JSONObject getResponse() {
		return response;
	}

	public boolean hasError() {
		return hasError;
	}

	public boolean hasAcces() {
		return getErrorCode() != -8509;
	}

	public int getErrorCode() {
		JSONObject error = response.optJSONObject("error", null);
		if (error == null)
			return 0;
		return error.getInt("code");
	}

	public String getErrorMessage() {
		JSONObject error = response.optJSONObject("error", null);
		if (error == null)
			return "No Error";
		return error.getString("message");
	}

	public String getCompleteErrorMessage() {
		JSONObject error = response.optJSONObject("error", null);
		if (error == null)
			return "No Error";
		return "Server returned error code: " + error.getInt("code") + "(" + error.getString("message") + ")";
	}

	/* Static */
	public static boolean hasError(JSONObject json) {
		JSONObject error = json.optJSONObject("error", null);
		return error != null;
	}

	public static int getErrorCode(JSONObject json) {
		if (!hasError(json))
			return 0;
		return json.optJSONObject("error").getInt("code");
	}
}
