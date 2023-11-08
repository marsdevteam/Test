package com.stu.app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long studentId;

	private String father;

	private String email;
	
	private String mobile;

	private String university;

	private String course;

	private String comments;

	private Date createdOn;

	private Date modifiedOn;

	private boolean approved;
}
