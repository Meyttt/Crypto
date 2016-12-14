package ru.mirea.common;

/**
 * Created by Admin on 14.12.2016.
 */
public class VerificationData {
	private String login;
	private String password;

	public String getPassword() {
		return password;
	}

	public String getLogin() {

		return login;
	}

	public VerificationData(String login, String password) {
		this.login = login;
		this.password = password;
	}
}
