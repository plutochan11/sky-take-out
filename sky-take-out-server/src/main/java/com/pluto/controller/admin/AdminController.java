package com.pluto.controller.admin;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Admin;
import com.pluto.model.dto.AdminAddDto;
import com.pluto.model.dto.AdminListDto;
import com.pluto.model.dto.AdminLoginDto;
import com.pluto.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/management/admin")
@Tag(name = "Admin", description = "Admin related APIs")
public class AdminController {
	@Resource
	private AdminService adminService;

	@Operation(summary = "Update the admin info")
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Admin admin) {
		adminService.update(admin);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find an admin info by id")
	@ApiResponse(responseCode = "200", description = "Return the admin info")
	@GetMapping("/find/{id}")
	public ResponseEntity<Admin> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(adminService.findById(id));
	}

	@Operation(summary = "Add a new admin")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody AdminAddDto adminAddDto) {
		// Check if there's already an admin with the same username
		adminService.checkUsername(adminAddDto.getUsername());

		adminService.add(adminAddDto);
		log.info("Added a new admin(username: {}, name: {})", adminAddDto.getUsername(), adminAddDto.getName());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Admin login")
	@PostMapping("/login")
	@ApiResponses(value = {
		  @ApiResponse(responseCode = "200", description = "Login successful, return a JWT token"),
		  @ApiResponse(responseCode = "404", description = "Admin account not found or incorrect password")
	})
	public ResponseEntity<String> login(@RequestBody AdminLoginDto adminLoginDto) {
		// Verify admin by username and password
		adminService.verifyUsername(adminLoginDto.getUsername());
		adminService.verifyPassword(adminLoginDto.getUsername(), adminLoginDto.getPassword());

		// Generate a JWT token with admin id, username and the real admin name
		String jwt = adminService.generateJwt(adminLoginDto.getUsername());
		log.info("Admin(username: {}) logged in", adminLoginDto.getUsername());
		return ResponseEntity.ok(jwt);
	}

	@ApiResponse(responseCode = "200", description = "Return paginated admin info and pagination details")
	@Operation(summary = "Return admin info with pagination")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Admin>> list(@RequestBody AdminListDto adminListDto) {
		return ResponseEntity.ok(adminService.list(adminListDto));
	}
}
