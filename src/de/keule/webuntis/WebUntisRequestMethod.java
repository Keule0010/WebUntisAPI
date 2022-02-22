package de.keule.webuntis;

public enum WebUntisRequestMethod {

	GET_USER_DATA_2017("getUserData2017"),
	
	LOGOUT("logout"), 
	LOGIN("authenticate"),
	SEARCH_SCHOOL("searchSchool"),
	GET_APP_SHARED_SECRET("getAppSharedSecret"),
	GET_TIMETABLE("getTimetable"),
	GET_LAST_IMPORT("getLatestImportTime"),
	GET_TEACHERS("getTeachers"),
	GET_STUDENTS("getStudents"),
	GET_KLASSEN("getKlassen"),
	GET_SUBJECTS("getSubjects"),
	GET_ROOMS("getRooms"),
	GET_DEPARTMENTS("getDepartments"),
	GET_HOLIDAYS("getHolidays"),
	GET_TIMEGRID("getTimegridUnits"),
	GET_STATUS_DATA("getStatusData"),
	GET_CURRENT_SCHOOLYEAR("getCurrentSchoolyear"),
	GET_SCHOOLYEARS("getSchoolyears"),
	GET_PERSON_ID("getPersonId"),
	GET_SUBSTITUTIONS("getSubstitutions"),
	GET_CLASSREG_EVENTS("getClassregEvents"),
	GET_EXAMS("getExams"),
	GET_EXAM_TYPES("getExamTypes"),
	GET_TIMETABLE_WITH_ABSENCES("getTimetableWithAbsences"),
	GET_CLASSREG_CATEGORIES("getClassregCategories"),
	GET_CLASSREG_CATEGORY_GROUPS("getClassregCategoryGroups"),
	;
	
	public final String NAME;

	private WebUntisRequestMethod(String NAME) {
		this.NAME = NAME;
	}
}
