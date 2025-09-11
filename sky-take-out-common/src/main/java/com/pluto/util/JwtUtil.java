package com.pluto.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
	@Value("${jwt.secret}")
	private static String secret;
	private static final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	@Value("${jwt.expiry}")
	private static Long expiration;

	/**
	 * Generate a JWT token with the given claims.
	 * @param claims
	 * @return
	 */
	public static String generate(Map<String, ?> claims) {
		return Jwts.builder()
			  .claims(claims)
			  .expiration(Date.from(Instant.now().plusSeconds(expiration)))
			  .signWith(secretKey)
			  .compact();
	}

	/**
	 * Parse the given JWT token and return the claims.
	 * @param jwt
	 * @return
	 */
	public static Claims parse (String jwt) {
		return Jwts.parser()
			  .verifyWith(secretKey)
			  .build()
			  .parseSignedClaims(jwt)
			  .getPayload();
	}
}
