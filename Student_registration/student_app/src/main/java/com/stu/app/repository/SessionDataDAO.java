package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stu.app.model.SessionData;

/**
 * This interface defines data access operations for the 'SessionData' entity
 * using Spring Data JPA. It extends the JpaRepository interface to provide
 * common CRUD functionality.
 */
@Repository
public interface SessionDataDAO extends JpaRepository<SessionData, Long> {

	/**
	 * Retrieves a session data by the access token.
	 *
	 * @param token The access token to search for.
	 * @return The session data associated with the given access token, or null if
	 *         not found.
	 */
	SessionData findByAccessToken(String token);

	/**
	 * Retrieves a session data by the login ID.
	 *
	 * @param loginId The login ID to search for.
	 * @return The session data associated with the given login ID, or null if not
	 *         found.
	 */
	SessionData findByLoginId(String loginId);
}
