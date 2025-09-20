package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.enums.ErrorMessage;
import com.pluto.exception.AccountNotFoundExecption;
import com.pluto.exception.DuplicateUsernameException;
import com.pluto.exception.IncorrectPasswordException;
import com.pluto.mapper.AdminMapper;
import com.pluto.model.Admin;
import com.pluto.model.dto.AdminAddDto;
import com.pluto.model.dto.AdminListDto;
import com.pluto.model.dto.AdminLoginDto;
import com.pluto.service.AdminService;
import com.pluto.util.JwtUtil;
import com.pluto.util.PasswordHasher;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
	@Resource
	private AdminMapper adminMapper;
	@Resource
	private PasswordHasher passwordHasher;

	@Override
	public void checkUsername(String username) throws DuplicateUsernameException {
		int count = adminMapper.countByUsername(username);
		if (count == 0) {
			return;
		} else {
			throw new DuplicateUsernameException(ErrorMessage.DUPLICATE_USERNAME.getMessage());
		}
	}

	@Override
	public void add(AdminAddDto adminAddDto) {
		String hashedPassword = passwordHasher.hash(adminAddDto.getPassword());
		adminAddDto.setPassword(hashedPassword);
		adminMapper.add(adminAddDto);
	}

	@Override
	public PageInfo<Admin> list(AdminListDto adminListDto) {
		PageHelper.startPage(adminListDto.pageNo(), adminListDto.pageSize());
		if (adminListDto.name() == null) {
			List<Admin> admins = adminMapper.findAll();
			return new PageInfo<>(admins);
		}
		List<Admin> admins = adminMapper.findByName(adminListDto.name());
		return new PageInfo<>(admins);
	}

	@Override
	public Admin findById(Integer id) {
		Admin admin = adminMapper.findById(id);
		if (admin != null) {
			// Hide the password before returning
			admin.setPassword("***");
			return admin;
		} else {
			throw new AccountNotFoundExecption(ErrorMessage.USER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public void update(Admin admin) {
		// Check if an admin with the given id exists
		Admin originalAdmin = adminMapper.findById(admin.getId());
		if (originalAdmin == null) {
			throw new AccountNotFoundExecption(ErrorMessage.USER_NOT_FOUND.getMessage());
		}
		// Verify if the new username is taken
		if (adminMapper.countByUsername(admin.getUsername()) != 0) {
			throw new IllegalArgumentException("Username has been used by another admin");
		}

		// Hash the password before the update
		String hashedPassword = passwordHasher.hash(admin.getPassword());
		admin.setPassword(hashedPassword);
		adminMapper.updateById(admin);
	}

	@Override
	public String login(AdminLoginDto dto) {
		String username = dto.getUsername();
		String password = dto.getPassword();
		Optional<Admin> admin = Optional.ofNullable(adminMapper.findByUsername(username));
		// If user not found given the username
		if (admin.isEmpty()) {
			throw new AccountNotFoundExecption(ErrorMessage.USER_NOT_FOUND.getMessage());
		}

		// if wrong password
		String truePassword = admin.get().getPassword();
		if (!passwordHasher.verify(password, truePassword)) {
			throw new IncorrectPasswordException(ErrorMessage.INCORRECT_PASSWORD.getMessage());
		}

		Admin realAdmin = admin.get();
		// Generate a JWT token with admin id, username and the real admin name
		return generateJwt(realAdmin.getId(), realAdmin.getUsername(), realAdmin.getName());
	}

	@Override
	public Admin findByUsername(String username) {
		return adminMapper.findByUsername(username);
	}

	private String generateJwt(Integer id, String username, String name) {
		Map<String, String> claims = Map.of(
			  "admin id", String.valueOf(id),
			  "username", username,
			  "name", name
		);
		return JwtUtil.generate(claims);
	}
}
