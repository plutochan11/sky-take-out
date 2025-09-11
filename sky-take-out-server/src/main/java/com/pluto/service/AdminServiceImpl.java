package com.pluto.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.enums.ErrorMessage;
import com.pluto.exception.AccountNotFoundExecption;
import com.pluto.exception.DuplicateUsernameException;
import com.pluto.exception.IncorrectPasswordException;
import com.pluto.mapper.AdminMapper;
import com.pluto.model.Admin;
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
	public void add(Admin admin) {
		String hashedPassword = passwordHasher.hash(admin.getPassword());
		admin.setPassword(hashedPassword);
		adminMapper.add(admin);
	}

	@Override
	public PageInfo<Admin> list(Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Admin> admins = adminMapper.findAll();
		return new PageInfo<>(admins);
	}
}
