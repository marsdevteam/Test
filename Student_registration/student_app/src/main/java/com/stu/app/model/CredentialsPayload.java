package com.stu.app.model;

import lombok.Data;

/**
 * A helper class for handling user credentials payload.
 */
@Data
public class CredentialsPayload {

	private String mobile;
	private String password;

}
