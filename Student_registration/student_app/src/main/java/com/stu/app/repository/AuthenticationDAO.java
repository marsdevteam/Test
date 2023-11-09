package com.stu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stu.app.model.User;

/**
 * This interface defines data access operations for the 'User' entity using
 * Spring Data JPA. It extends the JpaRepository interface to provide common
 * CRUD functionality.
 */
@Repository
public interface AuthenticationDAO extends JpaRepository<User, Long> {

	/**
	 * Retrieves a user by their mobile number if the user is active.
	 *
	 * @param mobile The mobile number of the user to retrieve.
	 * @return The user with the specified mobile number if found and active, or
	 *         null if not found or not active.
	 */
	@Query("SELECT u FROM User u WHERE u.mobile = ?1 and u.active = true")
	 User findByMobile(String mobile);
}
