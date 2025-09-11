package com.pluto.controller.admin;

import com.pluto.model.Admin;
import com.pluto.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/management/admin")
public class AdminController {
	@Resource
	private AdminService adminService;

	@Operation(summary = "Add a new admin")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Admin admin) {
		// Check if there's already an admin with the same username
		adminService.checkUsername(admin.getUsername());

		adminService.add(admin);
		log.info("Added a new admin({}, {})", admin.getUsername(), admin.getName());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Admin login")
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Admin admin) {
		// Verify admin by username and password
		adminService.verifyUsername(admin.getUsername());
		adminService.verifyPassword(admin.getUsername(), admin.getPassword());

		// Generate a JWT token with admin id, username and the real admin name
		String jwt = adminService.generateJwt(admin.getUsername());
		log.info("Admin({}, {}) logged in", admin.getUsername(), admin.getName());
		return ResponseEntity.ok(jwt);
	}
}
