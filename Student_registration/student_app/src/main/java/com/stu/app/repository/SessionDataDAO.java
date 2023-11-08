package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.SessionData;

@Repository
public interface SessionDataDAO extends JpaRepository<SessionData, Long> {

	@Query("SELECT s FROM SessionData s WHERE s.accessToken = ?1")
	public SessionData findByToken(String token);

	@Query("SELECT s FROM SessionData s WHERE s.loginId = ?1")
	public SessionData findByLoginId(String loginId);
}
