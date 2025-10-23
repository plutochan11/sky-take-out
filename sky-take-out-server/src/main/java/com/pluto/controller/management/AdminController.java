package com.pluto.controller.management;

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
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "Admin related APIs")
@Slf4j
@RestController
@RequestMapping("/management/admin")
public class AdminController {
	@Resource
	private AdminService adminService;

	@Operation(summary = "Login")
	@ApiResponses(value = {
		  @ApiResponse(responseCode = "200", description = "Login successful, return a JWT token in the body and admin ID in the X-Request-ID header"),
		  @ApiResponse(responseCode = "401", description = "Incorrect password"),
		  @ApiResponse(responseCode = "404", description = "Admin not found")
	})
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AdminLoginDto dto, HttpServletResponse response) {
		String jwt = adminService.login(dto);
		Integer adminId = adminService.findByUsername(dto.getUsername()).getId();
		response.setHeader("X-Request-ID", adminId.toString());
		return ResponseEntity.ok(jwt);
	}

	@Operation(summary = "Get admins with pagination and filters")
	@ApiResponse(responseCode = "200")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Admin>> get(@RequestBody AdminListDto adminListDto) {
		return ResponseEntity.ok(adminService.list(adminListDto));
	}

	@Operation(summary = "Get an admin by id")
	@ApiResponse(responseCode = "200", description = "Return the admin info")
	@GetMapping("/find/{id}")
	public ResponseEntity<Admin> get(@PathVariable Integer id) {
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

	@Operation(summary = "Update the admin info")
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Admin admin) {
		adminService.update(admin);
		return ResponseEntity.ok().build();
	}
}
