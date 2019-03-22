package com.desire3d;

public class DBConfiguration {

	String user = System.getenv("AUTH_MS_DB_USER");
	String password = System.getenv("AUTH_MS_DB_PASSWORD");
	String connectionurl = System.getenv("AUTH_DB_CONNECTION_URL");

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getConnectionurl() {
		return connectionurl;
	}

}
