package com.stu.app.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stu.app.model.Application;
import com.stu.app.model.CredentialsPayload;
import com.stu.app.model.Student;
import com.stu.app.model.Tokens;
import com.stu.app.service.AuthenticationService;
import com.stu.app.service.CourseService;
import com.stu.app.service.StudentService;
import com.stu.app.service.UniversityService;
import com.stu.app.utils.Secured;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author user 
 * This is a controller class. All end points are implemented here.
 * Student registration, login and applied for courses's end points are here.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentResource {

	private final StudentService studentService;

	private final AuthenticationService authenticationService;

	private final UniversityService universityService;

	private final CourseService courseService;

	/**
	 * @param student model in which all the student related information is
	 *                available
	 * @return response as ok with json string message when execution is successful
	 *         This end point is responsible for registering the new student
	 */
	@PostMapping("/student/register")
	public ResponseEntity<String> registerStudent(@RequestBody Student student) {
		log.info("Received request for registering new student");
		String confirmationMessage = studentService.registerStudent(student);
		log.info("Student registered successfully");
		return ResponseEntity.ok(confirmationMessage);
	}

	/**
	 * @param credentialsPayload
	 * @return response as JWT access_token after successfully login. This end point
	 *         is responsible for student login using their mobile number and
	 *         password.
	 */
	@PostMapping("/student/login")
	public ResponseEntity<Tokens> login(@RequestBody CredentialsPayload credentialsPayload) {
		log.info("Received request for login..");
		Tokens token = authenticationService.login(credentialsPayload);
		log.info("login successful and token returend..");
		return ResponseEntity.ok(token);
	}

	/**
	 * @param application
	 * @return response as ok with json string message when execution is successful.
	 *         This end point is responsible for applying for courses.
	 */
	@Secured
	@PostMapping("/student/apply")
	public ResponseEntity<String> applyCourse(@RequestBody Application application) {
		log.info("Received request for applying course");
		String confirmationMessage = studentService.applyForCourse(application);
		log.info("Applied Application successfully");
		return ResponseEntity.ok(confirmationMessage);
	}

	/**
	 * @return list of courses
	 */
	@GetMapping("/courses")
	public ResponseEntity<List<String>> getAllCourses() {
		log.info("Received request for fetching all courses");
		List<String> courseLise = courseService.getAllCourses();
		log.info("All courses are fetched successfully");
		return ResponseEntity.ok(courseLise);
	}

	/**
	 * @return list of university
	 */
	@Secured
	@GetMapping("/universities")
	public ResponseEntity<List<String>> getAllUniversities() {
		log.info("Received request for fetching all universities");
		List<String> universityList = universityService.getAllUniversities();
		log.info("All universities are fetched successfully");
		return ResponseEntity.ok(universityList);
	}

	@Secured
	@GetMapping("/student/application")
	public ResponseEntity<List<Application>> getAllApplicationOfCurrentUser() {
		log.info("Received request for fetching all application of current login user");
		List<Application> applicationList = studentService.getAllApplicationByStudentId();
		log.info("All application are fetched successfully");
		return ResponseEntity.ok(applicationList);
	}
}
