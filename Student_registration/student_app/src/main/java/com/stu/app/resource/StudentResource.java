package com.stu.app.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class StudentResource {

	@Autowired
	private StudentService studentService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UniversityService universityService;

	@Autowired
	private CourseService courseService;

	@PostMapping("/student/register")
	public ResponseEntity<String> registerStudent(@RequestBody Student student) {
		log.info("Received request for registering new student");
		String res = studentService.registerStudent(student);
		log.info("Student registered successfully");
		return ResponseEntity.ok(res);
	}

	@PostMapping("/student/login")
	public ResponseEntity<Tokens> login(@RequestBody CredentialsPayload credentialsPayload) {
		log.info("Received request for login..");
		Tokens token = authenticationService.login(credentialsPayload);
		log.info("login successful and token returend..");
		return ResponseEntity.ok(token);
	}

	@Secured
	@PostMapping("/student/apply")
	public ResponseEntity<String> applyCourse(@RequestBody Application application) {
		log.info("Received request for applying course");
		String res = studentService.applyForCourse(application);
		log.info("Applied Application successfully");
		return ResponseEntity.ok(res);
	}

	@GetMapping("/courses")
	public ResponseEntity<List<String>> getAllCourse() {
		log.info("Received request for fetching all courses");
		List<String> res = courseService.getAllCourse();
		log.info("All courses are fetched successfully");
		return ResponseEntity.ok(res);
	}

	@Secured
	@GetMapping("/universities")
	public ResponseEntity<List<String>> getAllUniversity() {
		log.info("Received request for fetching all universities");
		List<String> res = universityService.getAllUniversity();
		log.info("All universities are fetched successfully");
		return ResponseEntity.ok(res);
	}
}
