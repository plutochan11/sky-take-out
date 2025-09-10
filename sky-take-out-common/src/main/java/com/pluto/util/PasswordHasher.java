package com.pluto.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public String hash(String plainPassword) {
		return bCryptPasswordEncoder.encode(plainPassword);
	}

	public boolean verify(String rawPassword, String hashedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
	}
}
