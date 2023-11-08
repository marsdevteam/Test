package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.User;

@Repository
public interface AuthenticationDAO extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.mobile = ?1 and u.active = true")
	public User findByMobile(String mobile);
}
