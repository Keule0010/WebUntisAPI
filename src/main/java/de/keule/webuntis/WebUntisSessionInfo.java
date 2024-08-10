package de.keule.webuntis;

public class WebUntisSessionInfo {
  private String schoolname;
  private String serverUrl;
  private String requestId;
  private String sid;

  private int personId;
  private int klasseId;
  private int type;

  public WebUntisSessionInfo(String serverUrl, String requestId) {
    this.serverUrl = checkServerUrl(serverUrl);
    this.requestId = requestId;
  }

  /* Getters */
  public String getSessionId() {
    return sid;
  }

  public String getRequestId() {
    return requestId;
  }

  public int getType() {
    return type;
  }

  public int getPersonId() {
    return personId;
  }

  public int getKlasseId() {
    return klasseId;
  }

  public String getServer() {
    return serverUrl;
  }

  public String getSchoolname() {
    return schoolname;
  }

  public boolean isActive() {
    return sid != null && schoolname != null && !sid.isEmpty() && !schoolname.isEmpty();
  }

  /* Setters */
  public void setRequestId(String id) {
    this.requestId = id;
  }

  public void setSchoolName(String schoolname) {
    this.schoolname = schoolname;
  }

  public void setSessionId(String sid) {
    this.sid = sid;
  }

  public void setPersonType(int type) {
    this.type = type;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  public void setKlasseId(int klassenId) {
    this.klasseId = klassenId;
  }

  /* Static */
  private static String checkServerUrl(String server) {
    if (server.contains("http") || server.contains("https")) {
      if (server.charAt(server.length() - 1) != '/')
        return server + "/";
      return server;
    } else if (server.contains("webuntis"))
      return "https://" + server + "/";
    else
      return "https://" + server + ".webuntis.com/";
  }
}
