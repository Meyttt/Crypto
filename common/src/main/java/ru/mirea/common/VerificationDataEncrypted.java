package ru.mirea.common;

import java.math.BigInteger;

/**
 * Created by Admin on 14.12.2016.
 */
public class VerificationDataEncrypted {
	BigInteger login;
	BigInteger password;

	public VerificationDataEncrypted(BigInteger login, BigInteger password) {
		this.login = login;
		this.password = password;
	}

	public BigInteger getLogin() {
		return login;
	}

	public BigInteger getPassword() {
		return password;
	}
}
