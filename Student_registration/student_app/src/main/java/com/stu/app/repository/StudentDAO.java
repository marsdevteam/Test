package com.stu.app.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.Student;

@Repository
public interface StudentDAO extends JpaRepository<Student, Long> {

	@Query("select s from Student s where s.name = :#{#student.name} and s.father = :#{#student.father} and s.dob = :#{#student.dob}")
	Student checkValidation(Student student);

	Student findByMobile(@NotNull(message = "mobile number is mandate") String mobile);

}
