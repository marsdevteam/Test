package com.stu.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;
import com.stu.app.model.CredentialsPayload;
import com.stu.app.model.SessionData;
import com.stu.app.model.Tokens;
import com.stu.app.model.User;
import com.stu.app.repository.AuthenticationDAO;
import com.stu.app.repository.SessionDataDAO;
import com.stu.app.utils.JwtTokenUtil;
import com.stu.app.utils.PasswordUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The `AuthenticationServiceImpl` class is responsible for managing user
 * authentication and generating authentication tokens. It provides methods for
 * user login, user retrieval by mobile number, and session data retrieval. It
 * also implements the `AuthenticationService` interface, which defines the
 * contract for authentication-related methods.
 *
 * @author saurabh
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationDAO authenticationDAO;

	@Autowired
	private SessionDataDAO sessionDataDAO;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	@Transactional("transactionManager")
	public Tokens login(CredentialsPayload credentialPayload) {
		log.info("Trying to login..");
		User user = authenticationDAO.findByMobile(credentialPayload.mobile());
		String encryptedPwd = PasswordUtils.encrypt(credentialPayload.password());
		log.debug("user fetched by User Name");
		if (user != null && encryptedPwd.equals(user.getPassword())) {
			log.debug("User Verified and trying to Generate Token..");
			return generateToken(credentialPayload);
		} else {
			log.error("Problem to authenticate, user not found");
			throw new ApplicationException(ErrorStatus.PROBLEM_TO_AUTHENTICATE, "User not found");
		}

	}

	@Override
	@Transactional("transactionManager")
	public User findUserByUserMobile(String mobile) {
		log.info("getting user by UserName");
		User user = authenticationDAO.findByMobile(mobile);
		log.info("User fetched by userName");
		return user;
	}

	@Override
	@Transactional("transactionManager")
	public SessionData findByToken() {
		log.info("getting sessionData by Token");
		String token = getBearerTokenHeader();
		SessionData sessionData = sessionDataDAO.findByAccessToken(token);
		log.info("sessionData fetched successfully by Token");
		return sessionData;
	}

	private Tokens generateToken(CredentialsPayload cp) {
		log.info("Generating Token");
		Tokens token = JwtTokenUtil.generateTokens(cp);
		log.debug("token generated..");
		SessionData sessionData = setTokenToSession(token, cp);
		SessionData sd = sessionDataDAO.findByLoginId(cp.mobile());
		if (sd != null) {
			log.debug("deleting previous sessionData if exist, before save new sessionData");
			sessionDataDAO.delete(sd);
		}
		log.debug("saving token detail to sessionData..");
		sessionDataDAO.save(sessionData);
		log.info("Token Generated and saved to sessionData");
		return token;
	}

	private SessionData setTokenToSession(Tokens token, CredentialsPayload cp) {
		SessionData sessionData = new SessionData();
		sessionData.setTokenType(token.tokenType());
		sessionData.setAccessToken(token.accessToken());
		sessionData.setLoginId(cp.mobile());
		sessionData.setCreatedOn(LocalDateTime.now());
		sessionData.setLoginCount(1);
		return sessionData;

	}

	private String getBearerTokenHeader() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
	}
}
