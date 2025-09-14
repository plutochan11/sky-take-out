package com.pluto.service;

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
import com.pluto.util.JwtUtil;
import com.pluto.util.PasswordHasher;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService{
	@Resource
	private AdminMapper adminMapper;
	@Resource
	private PasswordHasher passwordHasher;

	@Override
	public void verifyUsername(String username) {
		int count = adminMapper.existsByUsername(username);
		if (count == 1) {
			return;
		} else {
			throw new AccountNotFoundExecption(ErrorMessage.USER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public void verifyPassword(String username, String password) {
		String truePassword = adminMapper.findOneByUsername(username).getPassword();
		if (passwordHasher.verify(password, truePassword)) {
			return;
		} else {
			throw new IncorrectPasswordException(ErrorMessage.INCORRECT_PASSWORD.getMessage());
		}
	}

	@Override
	public String generateJwt(String username) {
		Admin admin = adminMapper.findOneByUsername(username);
		if (admin != null) { // Proceed only if the admin is found
			Map<String, String> claims = Map.of(
				  "admin id", String.valueOf(admin.getId()),
				  "username", admin.getUsername(),
				  "name", admin.getName()
			);
			return JwtUtil.generate(claims);
		} else {
			throw new AccountNotFoundExecption(ErrorMessage.USER_NOT_FOUND.getMessage());
		}
	}

	@Override
	public void checkUsername(String username) throws DuplicateUsernameException {
		int count = adminMapper.existsByUsername(username);
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
		if (adminMapper.existsByUsername(admin.getUsername()) != 0) {
			throw new IllegalArgumentException("Username has been used by another admin");
		}

		// Hash the password before the update
		String hashedPassword = passwordHasher.hash(admin.getPassword());
		admin.setPassword(hashedPassword);
		adminMapper.updateById(admin);
	}
}
