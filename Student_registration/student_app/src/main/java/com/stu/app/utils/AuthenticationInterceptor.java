package com.stu.app.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.app.exception.ApplicationException;
import com.stu.app.model.SessionData;
import com.stu.app.model.User;
import com.stu.app.service.AuthenticationService;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	public static final String AUTHENTICATION_SCHEME_JWT = "jwt";

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MDC.put("CorrelationId", getCorrelationId());
		if (handler instanceof HandlerMethod) {
			final HandlerMethod handlerMethod = (HandlerMethod) handler;
			final Method method = handlerMethod.getMethod();

			if (method.isAnnotationPresent(Secured.class)) {
				try {
					SessionData sessionData = authenticationService.findByToken();
					if (sessionData != null) {
						Claims claims = JwtTokenUtil.validateToken(sessionData.getAccessToken());
						User user = authenticationService.findUserByUserMobile(JwtTokenUtil.getUserName(claims));

						if (user == null) {
							return abortWithUnauthorized(response);
						}

						if (JwtTokenUtil.isTokenExpired(claims)) {
							return abortWithSessionFailure(response);
						}
					} else {
						return abortWithUnauthorized(response);
					}
				} catch (ApplicationException e) {
					return abortWithSessionFailure(response);
				}
			}
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		MDC.remove("CorrelationId");
	}

	private String getCorrelationId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Aborts the filter chain with a 401 status code response and the authenticate
	 * header is sent along with the response.
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private boolean abortWithUnauthorized(HttpServletResponse response) throws Exception {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(objectMapper.writeValueAsString("Unauthorized Access"));
		return false;
	}

	/**
	 * Aborts the filter chain with a 504 status code response and the authenticate
	 * header is sent along with the response.
	 */
	private boolean abortWithSessionFailure(HttpServletResponse response) throws Exception {
		response.setStatus(HttpStatus.GATEWAY_TIMEOUT.value());
		response.getWriter().write(objectMapper.writeValueAsString("Gateway Timeout"));
		return false;
	}
}
