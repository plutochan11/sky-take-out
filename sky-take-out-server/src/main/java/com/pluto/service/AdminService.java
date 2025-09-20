package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.exception.AccountNotFoundExecption;
import com.pluto.exception.DuplicateUsernameException;
import com.pluto.exception.IncorrectPasswordException;
import com.pluto.model.Admin;
import com.pluto.model.dto.AdminAddDto;
import com.pluto.model.dto.AdminListDto;
import com.pluto.model.dto.AdminLoginDto;
import jakarta.servlet.http.HttpSession;

public interface AdminService {
	/**
	 * Check if there's already an admin with the same username
	 * @param username The admin username
	 * @throws DuplicateUsernameException An DuplicateUsernameException if the username is already taken
	 */
	void checkUsername(String username) throws DuplicateUsernameException;

	/**
	 * Add a new admin
	 * @param adminAddDto The adminAddDto to be added
	 * @exception DuplicateUsernameException An DuplicateUsernameException if this username is already taken
	 */
	void add(AdminAddDto adminAddDto) throws DuplicateUsernameException;

	/**
	 * Return a page of admins
	 * @param pageNo The page number
	 * @param pageSize The page size (how many records in a page)
	 * @return A page of admins
	 */
	PageInfo<Admin> list(AdminListDto adminListDto);

	/**
	 * Return the admin info by id
	 * @param id The admin id
	 * @return The admin info
	 */
	Admin findById(Integer id);

	/**
	 * Update the admin info
	 * @param admin The admin info to be updated
	 * Note: username cannot be updated
	 */
	void update(Admin admin);

	/**
	 * Login
	 * @param dto The {@link AdminLoginDto} containing username and password
	 * @return A JWT token from the admin id, username and the name if the login is successful
	 */
	String login(AdminLoginDto dto);

	/**
	 * Find an admin by username
	 * @param username The admin username
	 * @return The {@link Admin} if found
	 */
	Admin findByUsername(String username);
}
