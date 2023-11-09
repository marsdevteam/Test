package com.stu.app.utils;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;
import com.stu.app.model.CredentialsPayload;
import com.stu.app.model.Tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The JwtTokenUtil class provides utility methods for working with JSON Web
 * Tokens (JWTs) in a Spring application.
 */
@Component
public class JwtTokenUtil {

	private static final long JWT_TOKEN_VALIDITY = 120;
	private static final String SECRET = "student_app";
	private static final String TOKEN_TYPE = "jwt";

	/**
	 * Generates JWT tokens for a given CredentialsPayload and sets the token's
	 * validity period.
	 *
	 * @param credentialsPayload The payload containing user credentials.
	 * @return Tokens object with the generated JWT token and its validity period.
	 */
	public static Tokens generateTokens(CredentialsPayload credentialsPayload) {
		String token = doGenerateToken(credentialsPayload.mobile());
		LocalDateTime tokenValidity = LocalDateTime.now().plusMinutes(JWT_TOKEN_VALIDITY);
		return new Tokens(TOKEN_TYPE, token, tokenValidity);
	}

	/**
	 * Validates the given JWT token and returns the claims contained within it.
	 *
	 * @param token The JWT token to be validated.
	 * @return Claims object containing the JWT token's claims.
	 * @throws ApplicationException if the token is invalid or has expired.
	 */
	public static Claims validateToken(String token) {
		try {
			return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		} catch (Exception ex) {
			throw new ApplicationException(ErrorStatus.INVALID_HEADER, "Invalid Authorization header");
		}
	}

	/**
	 * Refreshes an existing JWT token by generating a new token and extending its
	 * validity period.
	 *
	 * @return Tokens object with the new JWT token and its extended validity
	 *         period.
	 * @throws ApplicationException if the existing token is invalid or has expired.
	 */
	public static Tokens refreshToken() {
		String accessToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
		try {
			Claims claims = JwtTokenUtil.validateToken(accessToken);
			String refreshToken = doGenerateToken(JwtTokenUtil.getUserName(claims));
			LocalDateTime tokenValidity = LocalDateTime.now().plusMinutes(JWT_TOKEN_VALIDITY);
			return new Tokens(TOKEN_TYPE, refreshToken, tokenValidity);
		} catch (Exception ex) {
			throw new ApplicationException(ErrorStatus.INVALID_HEADER, "Invalid Authorization header");
		}
	}

	private static String doGenerateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	/**
	 * Checks if a JWT token has expired based on its claims.
	 *
	 * @param claims The claims extracted from a JWT token.
	 * @return True if the token has expired, false otherwise.
	 */
	public static Boolean isTokenExpired(Claims claims) {
		Date expirationDate = claims.getExpiration();
		Date currentDate = new Date();
		return expirationDate.before(currentDate);
	}

	public static String getUserName(Claims claims) {
		return claims.getSubject();
	}

	private JwtTokenUtil() {
	}
}
