package com.stu.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * CourseServiceImpl is an implementation of the CourseService interface,
 * providing functionality to retrieve a list of courses. It retrieves the list
 * of courses from configuration properties and encapsulates them as a List of
 * strings.
 *
 * @author user
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

	@Value("${com.university.course}")
	private String[] courses;

	/**
	 * Retrieves a list of all available courses.
	 * 
	 * @return A List of strings representing all available courses.
	 * @throws ApplicationException if an error occurs during course retrieval.
	 */
	@Override
	public List<String> getAllCourses() {
		try {
			log.info("Service method start -> getAllCourses()");
			return Arrays.asList(courses);
		} catch (Exception e) {
			log.error("Exception occre while fetching all course details -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
		}

	}

}
