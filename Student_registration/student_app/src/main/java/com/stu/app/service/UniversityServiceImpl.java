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
public class UniversityServiceImpl implements UniversityService {

	@Value("${com.university.name}")
	private String[] university;

	@Override
	public List<String> getAllUniversity() {
		try {
			log.info("Service method start -> getAllUniversity()");
			return Arrays.asList(university);
		} catch (Exception e) {
			log.error("Exception occur while fetching all  university details -> [{}]", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

}
