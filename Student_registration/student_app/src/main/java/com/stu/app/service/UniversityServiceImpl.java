package com.stu.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stu.app.exception.ApplicationException;
import com.stu.app.exception.ErrorStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * UniversityServiceImpl is an implementation of the UniversityService interface,
 * providing functionality to retrieve a list of universities. It retrieves the list
 * of universities from configuration properties and encapsulates them as a List of
 * strings.
 *
 * @author user
 */
@Slf4j
@Service
public class UniversityServiceImpl implements UniversityService {

	@Value("${com.university.name}")
	private String[] universities;

	/**
	 * Retrieves a list of all available universities.
	 * 
	 * @return A List of strings representing all available universities.
	 * @throws ApplicationException if an error occurs during university retrieval.
	 */
	@Override
	public List<String> getAllUniversities() {
		try {
			log.info("Service method start -> getAllUniversities()");
			return Arrays.asList(universities);
		} catch (Exception e) {
			log.error("Exception occur while fetching all  university details", e);
			throw new ApplicationException(ErrorStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
		}
	}

}
