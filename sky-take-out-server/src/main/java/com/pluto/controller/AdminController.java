package com.pluto.controller;

import com.pluto.model.Admin;
import com.pluto.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private AdminService adminService;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Admin admin) {
		// Check if there's already an admin with the same username
		adminService.checkUsername(admin.getUsername());
		adminService.add(admin);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Admin admin) {
		// Verify admin by username and password
		adminService.verifyUsername(admin.getUsername());
		adminService.verifyPassword(admin.getUsername(), admin.getPassword());

		// Generate a JWT token with admin id, username and the real admin name
		String jwt = adminService.generateJwt(admin.getUsername());
		return ResponseEntity.ok(jwt);
	}
}
