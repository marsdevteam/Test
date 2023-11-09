package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.Student;

/**
 * This interface defines data access operations for the 'Student' entity using
 * Spring Data JPA. It extends the JpaRepository interface to provide common
 * CRUD functionality.
 */
@Repository
public interface StudentDAO extends JpaRepository<Student, Long> {

	/**
	 * Validates a student based on the provided 'Student' object, including mobile
	 * number, father'name, and DOB.
	 *
	 * @param student The Student object containing the data to check.
	 * @return The Student object if the data matches, or null if no match is found.
	 */
	@Query("select s from Student s where s.name = :#{#student.name} and s.fatherName = :#{#student.fatherName} and s.dob = :#{#student.dob}")
	Student checkStudentValidation(Student student);

	/**
	 * Retrieves a student by their mobile number.
	 *
	 * @param mobile The mobile number to search for.
	 * @return The Student object associated with the given mobile number, or null
	 *         if not found.
	 */
	Student findByMobile(String mobile);

}
