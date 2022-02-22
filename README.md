# Introduction

WebUntisAPI is a Java Wrapper that makes it easy to access the Webuntis JSON RPC 2.0 API. It is based on the [WebUntis JSON RPC 2.0 API](https://untis-sr.ch/wp-content/uploads/2019/11/2018-09-20-WebUntis_JSON_RPC_API.pdf) 
except for the secret login which is inspired from the [JavaScript WebUntis API](https://github.com/SchoolUtils/WebUntis).
It supports all request methods mentioned here [WebUntis JSON RPC 2.0 API](https://untis-sr.ch/wp-content/uploads/2019/11/2018-09-20-WebUntis_JSON_RPC_API.pdf) and is easy to use.
If you missing a method you can easily make your own request with the WebUntisRequestManager - see [Examples](#custom-request) for more details.

# Requirements

- JSON library for java [org.json](https://github.com/stleary/JSON-java)

# Java version

- Java 8 or higher

# Setup

Just download the latest [WebUntisAPI.jar](https://www.github.com/Keule22/WebUntisAPI/releases/latest) file, implement it into your project as well as the JSON-Library and you are ready to go.

# Examples

## Search for schools

```java
public class Main {
    
   	public static void main(String[] args) {
		try {
			/* Request schools */
			Schools schools = WebUntis.searchSchool("School name");

			/* Print data of each school */
			for (School school : schools.getSchools()) {
				System.out.println(school.getDisplayName());
				System.out.println(school.getLoginName());
				System.out.println(school.getAddress());
				System.out.println(school.getSchoolId());
				System.out.println(school.getServer());
				System.out.println(school.getServerUrl());
			}

			/* Catch exception */
		} catch (WebUntisException e) {
			e.printStackTrace();
		}
	}
}
```

## Login and get timetable (anonym/normal)
You can obtain the **school login name** and the **server** by using the [searching method](#search-for-schools). For example, if you have this **https://test.webuntis.com/** server URL, you can either use the entire URL or just **test** as the server parameter.
```java
public class Main {
    
    public static void main(String[] args) {
        try {
			/* Create new WebUntis object for normal login */
			WebUntis untis = new WebUntis("Login Name", "Password", "School login name", "Server");
			/* Create new WebUntis object for anonymous login */
			WebUntis untis = new WebUntis("School login name", "Server");

			/* Login create WebUntis session */
			untis.login();

			/* Request classes */
			Klassen classes = untis.getKlassen();
			/* Find class */
			Klasse clazz = classes.getKlasse("Class name", false);

			/* Check if class exists */
			if (clazz == null) {
				System.err.println("Class dosen't exist!");
				/* Logout to save resources on the server side */
				untis.logout();
				return;
			}

			/* Request timetable for class */
			Timetable timetable = untis.getTimetableForToday(clazz);

			/* Get first period of the timetable and print the start time */
			System.out.println(timetable.getFirstPeriod().getStartTime());

			/* Logout to save resources on the server side */
			untis.logout();

			/* Catch exception */
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
```

## Login and get timetable (secret)
```java
public class Main {
    
    public static void main(String[] args) {
       try {
			/* Create new WebUntis object for secret login */
			WebUntis untis = new WebUntis("Login Name", "Password/Secret", "School login name", "Server", true);

			/* Login create WebUntis session */
			untis.login();

			/* Request timetable for today */
			Timetable timetable = untis.getTimetableForToday();

			/* Get first period of the timetable and print the start time */
			System.out.println(timetable.getFirstPeriod().getStartTime());

			/* Logout to save resources on the server side */
			untis.logout();

			/* Catch exception */
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
```

## Custom request
You can set the **endpoint** to null then the default endpoint(Default:"/WebUntis/jsonrpc.do") will be taken. The same applies for the **params**(Default:"params":{}).

```java
public class Main {

    public static void main(String[] args) {
        try { 
           /* Create new WebUntis object for secret login */
			WebUntis untis = new WebUntis("Login Name", "Password/Secret", "School login name", "Server", true);

			/* Login create WebUntis session */
			untis.login();

			/* Send custom request */
			WebUntisResponse response = WebUntisRequestManager.requestPOST("Method name", untis.getSessionInfo(),
					"Endpoint", "School login name", "Params");

			/* Check response for errors */
			if (response.hasError()) {
				System.out.println("An error occurred! Server returned: " + response.getErrorCode());
				untis.logout();
				return;
			}

			/* Get JSON response data */
			JSONObject responseJS = response.getResponse();

			/* Process JSON data... */

			/* Logout to save resources on the server side */
			untis.logout();

			/* Catch exception */
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
```

# Documentation
I will create a more detailed documentation in the future but for now you have this samll section. 
### WebUntis
```java
/* Constructors */
WebUntis(String userName, String pas, String school, String server, boolean secretLogin);
WebUntis(String userName, String pas, String school, String server);
WebUntis(String school, String server);

/* WebUntis object*/
boolean login() throws IOException;
boolean logout() throws IOException;
Timetable getTimetableForToday() throws IOException;
Timetable getTimetableForToday(Klasse klasse) throws IOException;
Timetable getTimetableForToday(int type, int id) throws IOException;
Timetable getTimetableForWeek() throws IOException;
Timetable getTimetableForWeek(Klasse klasse) throws IOException;
Timetable getTimetableForWeek(int type, int id) throws IOException;
Timetable getTimetableForRange(int startDate, int endDate) throws IOException;
Timetable getTimetableForRange(int startDate, int endDate, Klasse klasse) throws IOException;
Timetable getTimetableForRange(int startDate, int endDate, int type, int id) throws IOException;
Timetable getTimetableFor(int date) throws IOException;
Timetable getTimetableFor(int date, int type, int id) throws IOException;
Teachers getTeachers() throws IOException;
Students getStudents() throws IOException;
Klassen getKlassen() throws IOException;
Klassen getKlassen(String schoolYearId) throws IOException;
Subjects getSubjects() throws IOException;
Rooms getRooms() throws IOException;
Departments getDepartments() throws IOException;
Holidays getHolidays() throws IOException;
Timegrid getTimegrid() throws IOException;
StatusData getStatusData() throws IOException;
Schoolyear getCurrentSchoolyear() throws IOException;
Schoolyears getSchoolyears() throws IOException;
long getLastImportTime() throws IOException;
int getPersonId(int type, String surename, String forename, int dateOfBirth);
Substitutions getSubstitutions(int startDate, int endDate, int departmentId) throws IOException;
ClassregEvents getClassregEvents(int startDate, int endDate) throws IOException;
ClassregEvents getClassregEvents(int startDate, int endDate, int type, int id) throws IOException;
ClassregEvents getClassregEvents(int startDate, int endDate, Integer type, String id, String keyType) throws IOException;
Exams getExams(int examTypeId, int startDate, int endDate) throws IOException;
JSONObject getExamTypes() throws IOException;
Absences getTimetableWithAbsences(int startDate, int endDate) throws IOException;
RemarkCategories getClassregCategories() throws IOException;
RemarkGroups getClassregCategoryGroups() throws IOException;
boolean sessionIsValid();
WebUntisSessionInfo getSessionInfo();
boolean isAnonymousLogin();
boolean isSecretLogin();
String getUserName();
String getSchool();
String getPas();

/* Static */
Schools WebUntis.searchSchool("School name") throws WebUntisException;
Schools WebUntis.searchSchool("School name", "Request id") throws WebUntisException;
```
### WebUntisRequestManager
```java
/* Static */
WebUntisResponse WebUntisRequestManager.requestPOST(WebUntisRequestMethod method, WebUntisSessionInfo session, String endPoint, String school, String params) throws IOException;
WebUntisResponse WebUntisRequestManager.requestPOST(String method, WebUntisSessionInfo session, String endPoint, String school, String params) throws IOException;
WebUntisResponse WebUntisRequestManager.requestGET(WebUntisSessionInfo session, String endPoint) throws IOException;
void WebUntisRequestManager.setUserAgent(String newUserAgent); // Default: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36 OPR/83.0.4254.46
String WebUntisRequestManager.getUserAgent();
void WebUntisRequestManager.setPrintRequest(boolean value); // Default: false
boolean WebUntisRequestManager.printRequests();
```

# License

This project is licensed under the MIT License. See the LICENSE file for more information.

# Disclaimer
**I take no responsibility. Use this at your own risk.**
