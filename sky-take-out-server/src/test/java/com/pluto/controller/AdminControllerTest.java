package com.pluto.controller;

import com.pluto.model.Admin;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(username = "admin", roles = "USER")
@SpringBootTest
class AdminControllerTest {
	@Resource
	AdminController adminController;
	Admin admin = Admin.builder()
		  .username("plutochan11")
		  .password("Chen@0216")
		  .name("Pluto Chan")
		  .build();

	@Test
	void add() {
		adminController.add(admin);
	}

	@Test
	void login() {
		adminController.login(admin);
	}
}