package com.stu.app.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;
import com.stu.app.model.Application;
import com.stu.app.model.SessionData;
import com.stu.app.model.Student;
import com.stu.app.model.User;
import com.stu.app.repository.ApplicationDAO;
import com.stu.app.repository.AuthenticationDAO;
import com.stu.app.repository.SessionDataDAO;
import com.stu.app.repository.StudentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private AuthenticationDAO authDAO;

	@Autowired
	private ApplicationDAO applicationDAO;

	@Autowired
	private SessionDataDAO sessionDAO;

	@Override
	public String registerStudent(Student student) {
		try {
			log.info("Service method start -> registerStudent()");
			validateStudent(student);
			student.setActive(true);
			student.setCreatedOn(new Date());

			log.info("Start saving student data to DB");
			Student savedStudent = studentDAO.save(student);

			log.info("Student saved successfully");
			saveAsUser(savedStudent);

			return "{\"message\": \"You are registerd successfully\"}";
		} catch (ApplicationException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Exception occure while saving the student data -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

	@Override
	public String applyForCourse(Application application) {
		try {
			log.info("Service method start -> applyForCourse()");

			Application previouseApplication = applicationDAO.validateStudentApplication(application);
			if (previouseApplication != null) {
				throw new ApplicationException(ErrorStatus.DUPLICATE_RECORD, "You have already applied");

			}
			String loginId = findByToken();
			Student studentData = studentDAO.findByMobile(loginId);

			application.setEmail(studentData.getEmail());
			application.setFather(studentData.getFather());
			application.setMobile(studentData.getMobile());
			application.setStudentId(studentData.getId());
			application.setCreatedOn(new Date());
			log.info("Saving application to DB");
			applicationDAO.save(application);

			return "{\"message\": \"Applied Successfully\"}";

		} catch (Exception e) {
			log.error("Exception occure while appling for course -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

	private void validateStudent(Student student) {
		Student registeredStudent = studentDAO.checkValidation(student);
		if (registeredStudent != null) {
			throw new ApplicationException(ErrorStatus.DUPLICATE_RECORD, "Already Registered Student");
		}

		Student regStu = studentDAO.findByMobile(student.getMobile());
		if (regStu != null) {
			throw new ApplicationException(ErrorStatus.DUPLICATE_RECORD, "Mobile number already registered");
		}

	}

	private void saveAsUser(Student savedStudent) {
		log.info("Start saving saved student details as user");
		User user = new User();
		user.setActive(true);
		user.setCreatedOn(savedStudent.getCreatedOn());
		user.setMobile(savedStudent.getMobile());
		user.setPassword(savedStudent.getPassword());
		log.info("Saving user details in DB");
		authDAO.save(user);

	}

	@Override
	public String findByToken() {
		log.info("getting sessionData by Token");
		String token = getBearerTokenHeader();
		SessionData sessionData = sessionDAO.findByToken(token);
		log.info("sessionData fetched successfully by Token");
		return sessionData.getLoginId();
	}

	private String getBearerTokenHeader() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization");
	}

}
