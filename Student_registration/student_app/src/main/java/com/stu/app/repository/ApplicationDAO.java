package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.Application;


@Repository
public interface ApplicationDAO extends JpaRepository<Application, Long> {


	@Query("select a from Application a where a.mobile = :#{#application.mobile} and a.course = :#{#application.course} and a.university = :#{#application.university}")
	Application validateStudentApplication(Application application);

}
