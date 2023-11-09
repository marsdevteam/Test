package com.stu.app.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.app.exception.ApplicationException;
import com.stu.app.model.SessionData;
import com.stu.app.service.AuthenticationService;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

/**
 * This class is an interceptor for handling authentication in Spring MVC. It
 * implements the HandlerInterceptor interface.
 */
@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

	private final ObjectMapper objectMapper;

	private final AuthenticationService authenticationService;

	/**
	 * This method is called before handling a request. It performs authentication
	 * and authorization checks.
	 *
	 * @param request  The HTTP request object.
	 * @param response The HTTP response object.
	 * @param handler  The request handler.
	 * @return True if the request is allowed to proceed, false if it should be
	 *         stopped.
	 * @throws Exception if an error occurs during request processing.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		MDC.put("CorrelationId", getCorrelationId());
		if (handler instanceof HandlerMethod handlerMethod) {
			final Method method = handlerMethod.getMethod();

			if (method.isAnnotationPresent(Secured.class)) {
				try {
					SessionData sessionData = authenticationService.findByToken();
					if (sessionData != null) {
						Claims claims = JwtTokenUtil.validateToken(sessionData.getAccessToken());

						if (Boolean.TRUE.equals(JwtTokenUtil.isTokenExpired(claims))) {
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

	/**
	 * It removes the `CorrelationId` from the MDC
	 */
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
	private boolean abortWithUnauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(objectMapper.writeValueAsString("Unauthorized Access"));
		return false;
	}

	/**
	 * Aborts the filter chain with a 504 status code response and the authenticate
	 * header is sent along with the response.
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private boolean abortWithSessionFailure(HttpServletResponse response) throws IOException {
		response.setStatus(HttpStatus.GATEWAY_TIMEOUT.value());
		response.getWriter().write(objectMapper.writeValueAsString("Gateway Timeout"));
		return false;
	}
}
