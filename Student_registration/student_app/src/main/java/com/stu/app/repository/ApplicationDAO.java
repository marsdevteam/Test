package com.stu.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.Application;

/**
 * This interface defines data access operations for the 'Application' entity using Spring Data JPA.
 * It extends the JpaRepository interface to provide common CRUD functionality.
 */
@Repository
public interface ApplicationDAO extends JpaRepository<Application, Long> {

    /**
     * Validates a student application based on the provided 'Application' object, including mobile number, course, and university.
     *
     * @param application The 'Application' object to validate.
     * @return The validated 'Application' entity if it exists, or null if no matching application is found.
     */
	@Query("select a from Application a where a.mobile = :#{#application.mobile} and a.course = :#{#application.course} and a.university = :#{#application.university}")
	Application validateStudentApplication(Application application);

	@Query("select a from Application a where a.mobile = ?1")
	List<Application> fetchAllByMobile(String loginId);


}
