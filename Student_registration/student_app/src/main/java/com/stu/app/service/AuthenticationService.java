package com.stu.app.service;

import com.stu.app.model.CredentialsPayload;
import com.stu.app.model.SessionData;
import com.stu.app.model.Tokens;
import com.stu.app.model.User;

public interface AuthenticationService {

	public Tokens login(CredentialsPayload cp);

	public SessionData findByToken();

	public User findUserByUserMobile(String userName);

}
