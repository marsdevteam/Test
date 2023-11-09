package com.stu.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.stu.app.utils.PasswordUtils;

import lombok.Data;

/**
 * Class for handling student data
 *
 */
@Data
@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String studentId;

	private String name;

	private String email;

	private String address;

	private String mobile;

	private String fatherName;

	private String mother;

	private String dob;

	private Date createdOn;

	private Date modifiedOn;

	private boolean active;

	private String university;

	private String course;

	private String password;

	@PrePersist
	@PreUpdate
	private void preRequest() {
		password = PasswordUtils.encrypt(password);
	}

}
