package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.exception.AccountNotFoundExecption;
import com.pluto.exception.DuplicateUsernameException;
import com.pluto.exception.IncorrectPasswordException;
import com.pluto.model.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminService {

	/**
	 * Check if an admin with the given username exists
	 * @param username The admin username
	 * @throws AccountNotFoundExecption An AccountNotFoundException if the admin does not exist
	 */
	void verifyUsername(String username) throws AccountNotFoundExecption;

	/**
	 * Check if an admin with the given username and password exists
	 * @param username The admin username
	 * @param password The admin password
	 * @throws IncorrectPasswordException An IncorrectPasswordException if the password is incorrect
	 */
	void verifyPassword(String username, String password) throws IncorrectPasswordException;

	/**
	 * Generate a JWT token with admin id, username and the real admin name
	 * @param username the admin username
	 * @return the JWT token
	 */
	String generateJwt(String username);

	/**
	 * Check if there's already an admin with the same username
	 * @param username The admin username
	 * @throws DuplicateUsernameException An DuplicateUsernameException if the username is already taken
	 */
	void checkUsername(String username) throws DuplicateUsernameException;

	/**
	 * Add a new admin
	 * @param admin The admin to be added
	 * @exception DuplicateUsernameException An DuplicateUsernameException if this username is already taken
	 */
	void add(Admin admin) throws DuplicateUsernameException;

	/**
	 * Return a page of admins
	 * @param pageNo The page number
	 * @param pageSize The page size (how many records in a page)
	 * @return A page of admins
	 */
	PageInfo<Admin> list(Integer pageNo, Integer pageSize);
}
