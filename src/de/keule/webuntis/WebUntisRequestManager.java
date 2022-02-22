package de.keule.webuntis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class WebUntisRequestManager {
	private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36 OPR/83.0.4254.46";
	private static final String DEFAULT_ENDPOINT = "/WebUntis/jsonrpc.do";
	private static boolean printRequests = false;

	public static WebUntisResponse requestPOST(WebUntisRequestMethod method, WebUntisSessionInfo session,
			String endPoint, String school, String params) throws IOException {
		return requestPOST(method.NAME, session, endPoint, school, params);
	}

	public static WebUntisResponse requestPOST(String method, WebUntisSessionInfo session, String endPoint,
			String school, String params) throws IOException {
		final URL url = new URL(session.getServer() + getEndPoint(endPoint) + "?school=" + school);
		if (params == null || params.isEmpty())
			params = "{}";
		final String request = "{\"id\":\"" + session.getRequestId() + "\",\"method\":\"" + method + "\",\"params\":"
				+ params + ",\"jsonrpc\":\"2.0\"}";

		if (printRequests) {
			System.out.println("Request[POST]: " + url.toExternalForm());
			System.out.println("Payload: " + request);
		}

		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setInstanceFollowRedirects(true);
		con.setRequestProperty("User-Agent", userAgent);
		con.setRequestProperty("Content-Type", "application/json");
		if (session.isActive())
			con.setRequestProperty("Cookie",
					"JSESSIONID=" + session.getSessionId() + "; schoolname=" + session.getSchoolname());

		// Write request body
		OutputStream outputStream = con.getOutputStream();
		outputStream.write(request.getBytes());

		// Read response
		BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = input.readLine()) != null) {
			stringBuilder.append(line);
		}

		// Get sessionId and schoolName
		if (!session.isActive()) {
			for (String key : con.getHeaderFields().keySet()) {
				for (String s : con.getHeaderFields().get(key)) {
					if (!s.contains("JSESSIONID") && !s.contains("schoolname"))
						continue;

					String[] split = s.split(";");
					for (String spl : split) {
						if (spl.contains("JSESSIONID")) {
							session.setSessionId(spl.replace("JSESSIONID=", ""));
						}
						if (spl.contains("schoolname")) {
							session.setSchoolName(spl.replace("schoolname=", "").replaceAll("\"", ""));
						}
					}
				}
			}
		}

		// Close
		outputStream.close();
		input.close();
		con.disconnect();

		return new WebUntisResponse(new JSONObject(stringBuilder.toString()));
	}

	public static WebUntisResponse requestGET(WebUntisSessionInfo session, String endPoint) throws IOException {
		final URL url = new URL(session.getServer() + endPoint);

		if (printRequests) {
			System.out.println("Request[GET]: " + url.toExternalForm());
		}

		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setInstanceFollowRedirects(true);
		con.setRequestProperty("User-Agent", userAgent);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Cookie",
				"JSESSIONID=" + session.getSessionId() + "; schoolname=" + session.getSchoolname());

		// Read response
		BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ((line = input.readLine()) != null) {
			stringBuilder.append(line);
		}

		// Close
		input.close();
		con.disconnect();

		return new WebUntisResponse(new JSONObject(stringBuilder.toString()));
	}

	private static String getEndPoint(String endPoint) {
		if (endPoint == null || endPoint.isEmpty())
			return DEFAULT_ENDPOINT;
		return endPoint;
	}

	public static void setUserAgent(String newUserAgent) {
		userAgent = newUserAgent;
	}
	
	public static String getUserAgent() {
		return userAgent;
	}

	public static boolean printRequests() {
		return printRequests;
	}

	public static void setPrintRequest(boolean value) {
		printRequests = value;
	}
}