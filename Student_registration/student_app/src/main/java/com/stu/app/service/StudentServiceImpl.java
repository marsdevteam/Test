package com.stu.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;
import com.stu.app.model.Application;
import com.stu.app.model.SessionData;
import com.stu.app.model.Student;
import com.stu.app.model.User;
import com.stu.app.repository.ApplicationDAO;
import com.stu.app.repository.AuthenticationDAO;
import com.stu.app.repository.StudentDAO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The `StudentServiceImpl` class is a service implementation of
 * `StudentService` interface responsible for handling various operations
 * related to students and applications. It provides methods for registering new
 * students and applying for courses, with corresponding input validation and
 * error handling.
 *
 * @author user
 */
@Slf4j
@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentDAO studentDAO;

	private final AuthenticationDAO authDAO;

	private final ApplicationDAO applicationDAO;

	private final AuthenticationService authService;

	/**
	 * Registers a new student.
	 *
	 * @param student The student to be registered.
	 * @return A JSON response indicating successful registration.
	 * @throws ApplicationException if the registration fails due to validation
	 *                              issues or unexpected errors.
	 */
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

	/**
	 * Apply for courses.
	 *
	 * @param application The application is applied.
	 * @return A JSON response indicating successful applied application.
	 * @throws ApplicationException if the application fails due to validation
	 *                              issues or unexpected errors.
	 */
	@Override
	public String applyForCourse(Application application) {
		try {
			log.info("Service method start -> applyForCourse()");

			log.info("Finding user mobile number");
			SessionData sessionData = authService.findByToken();
			application.setMobile(sessionData.getLoginId());
			Application previouseApplication = applicationDAO.validateStudentApplication(application);

			if (previouseApplication != null) {
				log.error("You have already applied for this course, please choose another course");
				throw new ApplicationException(ErrorStatus.DUPLICATE_RECORD, "You have already applied");

			}

			log.info("Finding user data using its mobile number");
			Student studentData = studentDAO.findByMobile(sessionData.getLoginId());

			application.setEmail(studentData.getEmail());
			application.setFatherName(studentData.getFatherName());
			application.setMobile(studentData.getMobile());
			application.setStudentId(studentData.getId());
			application.setCreatedOn(new Date());

			log.info("Saving application to DB");
			applicationDAO.save(application);

			return "{\"message\": \"Applied Successfully\"}"; // makw general Resoposne obje here
		} catch (ApplicationException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Exception occure while appling for course -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

	private void validateStudent(Student student) {
		Student registeredStudent = studentDAO.checkStudentValidation(student);
		if (registeredStudent != null) {
			log.error("Student is already registered");
			throw new ApplicationException(ErrorStatus.DUPLICATE_RECORD, "Already Registered Student");
		}

		Student regStu = studentDAO.findByMobile(student.getMobile());
		if (regStu != null) {
			log.error("Mobile number is already registered, please use another mobile number");
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
	public List<Application> getAllApplicationByStudentId() {
		try {
			log.info("Service method start -> getAllApplicationByStudentId()");
			SessionData sessionData = authService.findByToken();

			log.info("Fetching all application where mobile number -> [{}]", sessionData.getLoginId());
			List<Application> applicationList = applicationDAO.fetchAllByMobile(sessionData.getLoginId());
			if (applicationList.isEmpty()) {
				throw new ApplicationException(ErrorStatus.NOT_FOUND, "No Application found");
			}
			return applicationList;
		} catch (ApplicationException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Exception occur while fetching all application.", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

}
