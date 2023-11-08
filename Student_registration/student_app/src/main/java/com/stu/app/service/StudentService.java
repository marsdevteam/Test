package com.stu.app.service;

import com.stu.app.model.Application;
import com.stu.app.model.Student;

public interface StudentService {

	public String registerStudent(Student student);

	public String applyForCourse(Application application);

	public String findByToken();

}
