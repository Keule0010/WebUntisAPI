package de.keule.webuntis;

import java.io.IOException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.keule.webuntis.response.Absences;
import de.keule.webuntis.response.ClassregEvents;
import de.keule.webuntis.response.Departments;
import de.keule.webuntis.response.Exams;
import de.keule.webuntis.response.Holidays;
import de.keule.webuntis.response.Klasse;
import de.keule.webuntis.response.Klassen;
import de.keule.webuntis.response.RemarkCategories;
import de.keule.webuntis.response.RemarkGroups;
import de.keule.webuntis.response.Rooms;
import de.keule.webuntis.response.Schools;
import de.keule.webuntis.response.Schoolyear;
import de.keule.webuntis.response.Schoolyears;
import de.keule.webuntis.response.StatusData;
import de.keule.webuntis.response.Students;
import de.keule.webuntis.response.Subjects;
import de.keule.webuntis.response.Substitutions;
import de.keule.webuntis.response.Teachers;
import de.keule.webuntis.response.Timegrid;
import de.keule.webuntis.response.Timetable;

/*
 * Created: 17.02.2022
 * 
 * Author: Keule2 
 * 
 * */

public class WebUntis {
	private boolean anonymousLogin;
	private boolean secretLogin;
	private WebUntisSessionInfo session;
	private String userName;
	private String school;
	private String pas;

	/* Constructors */
	public WebUntis(String userName, String pas, String school, String server, boolean secretLogin) {
		this.session = new WebUntisSessionInfo(server, "ID");
		this.secretLogin = secretLogin;
		this.anonymousLogin = false;
		this.userName = userName;
		this.school = school;
		this.pas = pas;
	}

	public WebUntis(String userName, String pas, String school, String server) {
		this.session = new WebUntisSessionInfo(server, "ID");
		this.anonymousLogin = false;
		this.secretLogin = false;
		this.userName = userName;
		this.school = school;
		this.pas = pas;
	}

	public WebUntis(String school, String server) {
		this.session = new WebUntisSessionInfo(server, "ID");
		this.userName = "#anonymous#";
		this.anonymousLogin = true;
		this.school = school;
	}

	/* Login */
	public boolean login() throws IOException {
		if (session.isActive())
			throw new WebUntisException("Already logged in!");

		if (anonymousLogin)
			return anonymousLogin();

		if (secretLogin)
			return secretLogin(true);

		return normalLogin();
	}

	private boolean normalLogin() throws IOException {
		JSONObject params = new JSONObject();
		params.put("user", userName);
		params.put("password", pas);
		params.put("client", "CLIENT");

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.LOGIN, session, null,
				school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		JSONObject result = response.getResponse().getJSONObject("result");
		session.setPersonId(result.getInt("personId"));
		session.setPersonType(result.getInt("personType"));
		session.setKlasseId(result.getInt("klasseId"));
		return true;
	}

	private boolean anonymousLogin() throws IOException {
		JSONArray params = new JSONArray();
		params.put(new JSONObject().put("userName", userName).put("password", ""));

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_APP_SHARED_SECRET,
				session, "WebUntis/jsonrpc_intern.do", school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return secretLogin(false);
	}

	private boolean secretLogin(boolean getUserInfo) throws IOException {
		JSONArray params = new JSONArray();
		params.put(new JSONObject().put("auth", WebUntisAuthentication.getAuthObject(userName, pas)));
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_USER_DATA_2017,
				session, "WebUntis/jsonrpc_intern.do", school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		if (!getUserInfo)
			return true;

		response = WebUntisRequestManager.requestGET(session, "WebUntis/api/app/config");
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		JSONObject loginService = response.getResponse().getJSONObject("data").getJSONObject("loginServiceConfig");
		JSONObject user = loginService.getJSONObject("user");
		session.setPersonId(user.getInt("personId"));
		session.setPersonType(user.getInt("roleId"));

		response = WebUntisRequestManager.requestGET(session, "WebUntis/api/daytimetable/config");
		if (response.hasError())
			System.err.println("Couldn't retrieve klasseId. " + response.getCompleteErrorMessage());
		else {
			session.setKlasseId(response.getResponse().getJSONObject("data").getInt("klasseId"));
		}
		return true;
	}

	/* Logout */
	public boolean logout() throws IOException {
		if (!session.isActive())
			return true;

		WebUntisRequestManager.requestPOST(WebUntisRequestMethod.LOGOUT, session, "", school, null);
		session.setSchoolName("");
		session.setSessionId("");
		return true;
	}

	/* Get timetable */
	public Timetable getTimetableForToday() throws IOException {
		return getTimetable(session.getType(), session.getPersonId(), null, null);
	}

	public Timetable getTimetableForToday(Klasse klasse) throws IOException {
		return getTimetable(1, klasse.getId(), null, null);
	}

	public Timetable getTimetableForToday(int type, int id) throws IOException {
		return getTimetable(type, id, null, null);
	}

	public Timetable getTimetableForWeek() throws IOException {
		final int startDate = WebUntisDateOperations.getStartDateFromWeek(Calendar.getInstance(), 0);
		return getTimetable(session.getType(), session.getPersonId(), startDate,
				WebUntisDateOperations.addDaysToDate(startDate, 6));
	}

	public Timetable getTimetableForWeek(Klasse klasse) throws IOException {
		final int startDate = WebUntisDateOperations.getStartDateFromWeek(Calendar.getInstance(), 0);
		return getTimetable(1, klasse.getId(), startDate,
				WebUntisDateOperations.addDaysToDate(startDate, 6));
	}
	
	public Timetable getTimetableForWeek(int type, int id) throws IOException {
		final int startDate = WebUntisDateOperations.getStartDateFromWeek(Calendar.getInstance(), 0);
		return getTimetable(type, id, startDate, WebUntisDateOperations.addDaysToDate(startDate, 6));
	}

	public Timetable getTimetableForRange(int startDate, int endDate) throws IOException {
		return getTimetable(session.getType(), session.getPersonId(), startDate, endDate);
	}
	
	public Timetable getTimetableForRange(int startDate, int endDate, Klasse klasse) throws IOException {
		return getTimetable(1, klasse.getId(), startDate, endDate);
	}

	public Timetable getTimetableForRange(int startDate, int endDate, int type, int id) throws IOException {
		return getTimetable(type, id, startDate, endDate);
	}

	public Timetable getTimetableFor(int date) throws IOException {
		return getTimetable(session.getType(), session.getPersonId(), date, date);
	}

	public Timetable getTimetableFor(int date, int type, int id) throws IOException {
		return getTimetable(type, id, date, date);
	}

	private Timetable getTimetable(int type, int elementId, Integer startDate, Integer endDate) throws IOException {
		if (!sessionIsValid())
			throw new WebUntisException("Session isn't valid!");

		JSONArray op = new JSONArray();
		op.put("id");
		op.put("name");
		op.put("longname");
		op.put("externalkey");

		JSONObject options = new JSONObject();
		if (startDate != null)
			options.put("startDate", startDate);
		if (endDate != null)
			options.put("endDate", endDate);

		options.put("element", new JSONObject().put("id", elementId).put("type", type));

		options.put("showInfo", true);
		options.put("showLsText", true);
		options.put("showBooking", true);
		options.put("showLsNumber", true);
		options.put("showSubstText", true);
		options.put("showStudentgroup", true);
		options.put("roomFields", op);
		options.put("klasseFields", op);
		options.put("subjectFields", op);
		options.put("teacherFields", op);

		JSONObject params = new JSONObject();
		params.put("options", options);
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_TIMETABLE, session,
				null, school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Timetable(response.getResponse());
	}

	/* Get teachers */
	public Teachers getTeachers() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_TEACHERS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Teachers(response.getResponse());
	}

	/* Get students */
	public Students getStudents() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_STUDENTS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Students(response.getResponse());
	}

	/* Get klassen */
	public Klassen getKlassen() throws IOException {
		return getKlassen(null);
	}

	public Klassen getKlassen(String schoolYearId) throws IOException {
		JSONObject params = new JSONObject();
		if (schoolYearId != null)
			params.put("schoolyearId", anonymousLogin);

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_KLASSEN, session, null,
				school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Klassen(response.getResponse());
	}

	/* Get subjects */
	public Subjects getSubjects() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_SUBJECTS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Subjects(response.getResponse());
	}

	/* Get rooms */
	public Rooms getRooms() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_ROOMS, session, null,
				school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Rooms(response.getResponse());
	}

	/* Get departments */
	public Departments getDepartments() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_DEPARTMENTS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Departments(response.getResponse());
	}

	/* Get holidays */
	public Holidays getHolidays() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_HOLIDAYS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Holidays(response.getResponse());
	}

	/* Get timegrid */
	public Timegrid getTimegrid() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_TIMEGRID, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Timegrid(response.getResponse());
	}

	/* Get status data */
	public StatusData getStatusData() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_STATUS_DATA, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new StatusData(response.getResponse());
	}

	/* Get current schoolyear */
	public Schoolyear getCurrentSchoolyear() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_CURRENT_SCHOOLYEAR,
				session, null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Schoolyear(response.getResponse().getJSONObject("result"));
	}

	/* Get schoolyears */
	public Schoolyears getSchoolyears() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_SCHOOLYEARS, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Schoolyears(response.getResponse());
	}

	/* Get last import time */
	public long getLastImportTime() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_LAST_IMPORT, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return response.getResponse().getLong("result");
	}

	/* Search personId */
	public int getPersonId(int type, String surename, String forename, int dateOfBirth) {
		JSONObject params = new JSONObject();
		params.put("type", type);
		params.put("sn", surename);
		params.put("fn", forename);
		params.put("dob", dateOfBirth);

		try {
			WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_PERSON_ID, session,
					null, school, params.toString());
			if (response.hasError())
				return 0;

			return response.getResponse().getInt("result");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* Get substitutions */
	public Substitutions getSubstitutions(int startDate, int endDate, int departmentId) throws IOException {
		JSONObject params = new JSONObject();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("departmentId", departmentId);

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_SUBSTITUTIONS, session,
				null, school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Substitutions(response.getResponse());
	}

	/* Get classreg events */
	public ClassregEvents getClassregEvents(int startDate, int endDate) throws IOException {
		return getClassregEvents(startDate, endDate, null, null, null);
	}

	/* Get classreg events for a given student or klasse */
	public ClassregEvents getClassregEvents(int startDate, int endDate, int type, int id) throws IOException {
		return getClassregEvents(startDate, endDate, type, id + "", "id");
	}

	public ClassregEvents getClassregEvents(int startDate, int endDate, Integer type, String id, String keyType)
			throws IOException {
		JSONObject element = new JSONObject();
		element.put("id", id);
		element.put("type", type);
		element.put("keyType", keyType);

		JSONObject options = new JSONObject();
		options.put("startDate", startDate);
		options.put("endDate", endDate);
		options.put("element", element);

		JSONObject params = new JSONObject();
		if (type == null) {
			params.put("startDate", startDate);
			params.put("endDate", endDate);
		} else {
			params.put("options", options);
		}

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_CLASSREG_EVENTS,
				session, null, school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new ClassregEvents(response.getResponse());
	}

	/* Get exams */
	public Exams getExams(int examTypeId, int startDate, int endDate) throws IOException {
		JSONObject params = new JSONObject();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("examTypeId", examTypeId);

		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_EXAMS, session, null,
				school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Exams(response.getResponse());
	}

	/* Get exams types */
	public JSONObject getExamTypes() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_EXAM_TYPES, session,
				null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return response.getResponse();
	}

	/* Get timetable with absences */
	public Absences getTimetableWithAbsences(int startDate, int endDate) throws IOException {
		JSONObject options = new JSONObject();
		options.put("startDate", startDate);
		options.put("endDate", endDate);
		JSONObject params = new JSONObject();
		params.put("options", options);

		WebUntisResponse response = WebUntisRequestManager.requestPOST(
				WebUntisRequestMethod.GET_TIMETABLE_WITH_ABSENCES, session, null, school, params.toString());
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new Absences(response.getResponse());
	}

	/* Get remark categories */
	public RemarkCategories getClassregCategories() throws IOException {
		WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_CLASSREG_CATEGORIES,
				session, null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new RemarkCategories(response.getResponse());
	}

	/* Get all groups for remark categories */
	public RemarkGroups getClassregCategoryGroups() throws IOException {
		WebUntisResponse response = WebUntisRequestManager
				.requestPOST(WebUntisRequestMethod.GET_CLASSREG_CATEGORY_GROUPS, session, null, school, null);
		if (response.hasError())
			throw new WebUntisException(response.getCompleteErrorMessage());

		return new RemarkGroups(response.getResponse());
	}

	/* Validate session */
	public boolean sessionIsValid() {
		try {
			WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.GET_LAST_IMPORT,
					session, null, school, new JSONObject().toString());
			if (response.hasError())
				throw new WebUntisException(response.getCompleteErrorMessage());

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/* Setters */
	public void setRequestId(String id) {
		session.setRequestId(id);
	}

	/* Getters */
	public WebUntisSessionInfo getSessionInfo() {
		return session;
	}

	public boolean isAnonymousLogin() {
		return anonymousLogin;
	}

	public boolean isSecretLogin() {
		return secretLogin;
	}

	public String getUserName() {
		return userName;
	}

	public String getSchool() {
		return school;
	}

	public String getPas() {
		return pas;
	}

	/* Static */
	public static Schools searchSchool(String name) throws WebUntisException {
		return searchSchool(name, "ID");
	}

	public static Schools searchSchool(String name, String id) throws WebUntisException {
		JSONArray params = new JSONArray();
		params.put(new JSONObject().put("search", name));
		try {
			WebUntisResponse response = WebUntisRequestManager.requestPOST(WebUntisRequestMethod.SEARCH_SCHOOL,
					new WebUntisSessionInfo("https://schoolsearch.webuntis.com/", id), "schoolquery2", null,
					params.toString());
			if (response.hasError())
				throw new WebUntisException(response.getCompleteErrorMessage());

			return new Schools(response.getResponse());
		} catch (IOException | JSONException e) {
			throw new WebUntisException("Couldn't retrieve schools. Query: " + name, e);
		}
	}
}
