package com.stu.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SessionData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "token_type")
	private String tokenType;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "login_id")
	private String loginId;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "login_count")
	private int loginCount;

}
