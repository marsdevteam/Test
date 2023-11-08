package com.stu.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

	@Value("${com.university.course}")
	private String[] course;

	@Override
	public List<String> getAllCourse() {
		try {
			log.info("Service method start -> getAllCourse()");
			return Arrays.asList(course);
		} catch (Exception e) {
			log.error("Exception occre while fetching all course details -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
		}

	}

}
