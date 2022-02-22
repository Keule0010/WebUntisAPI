package de.keule.webuntis.response;

import org.json.JSONObject;

public class School {
	private boolean useMobileServiceUrlAndroid;
	private boolean useMobileServiceUrlIos;
	private String mobileServiceUrl;
	private String displayName;
	private String loginName;
	private String serverUrl;
	private JSONObject json;
	private String address;
	private String server;
	private int schoolId;

	public School(JSONObject info) {
		this.json = info;
		useMobileServiceUrlAndroid = info.optBoolean("useMobileServiceUrlAndroid");
		useMobileServiceUrlIos = info.optBoolean("useMobileServiceUrlIos");
		mobileServiceUrl = info.optString("mobileServiceUrl");
		displayName = info.optString("displayName");
		loginName = info.optString("loginName");
		serverUrl = info.optString("serverUrl");
		schoolId = info.optInt("schoolId");
		address = info.optString("address");
		server = info.optString("server");
	}

	public String getServer() {
		return server;
	}

	public boolean isUseMobileServiceUrlAndroid() {
		return useMobileServiceUrlAndroid;
	}

	public String getAddress() {
		return address;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getLoginName() {
		return loginName;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public boolean isUseMobileServiceUrlIos() {
		return useMobileServiceUrlIos;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getMobileServiceUrl() {
		return mobileServiceUrl;
	}

	public JSONObject getJSON() {
		return json;
	}
}