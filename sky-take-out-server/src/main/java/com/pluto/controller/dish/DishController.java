package com.pluto.controller.dish;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Admin;
import com.pluto.model.Dish;
import com.pluto.model.dto.AdminListDto;
import com.pluto.model.dto.DishDto;
import com.pluto.model.dto.DishListDto;
import com.pluto.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/dish")
@Tag(name = "Dish", description = "Dish related APIs")
public class DishController {
	@Resource private DishService dishService;

	@Operation(summary = "Delete a dish by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		dishService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Toggle dish status (e.g., available/unavailable) by id")
	@GetMapping("/status/{id}")
	public ResponseEntity<?> toggleStatus(@PathVariable Integer id) {
		dishService.toggleStatus(id);
		return ResponseEntity.ok().build();
	}

	@ApiResponse(responseCode = "200", description = "Return paginated dish info and pagination details")
	@Operation(summary = "Return dish info with pagination")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Dish>> list(@io.swagger.v3.oas.annotations.parameters.RequestBody(
		  description = "Required fields: pageNo, pageSize. Optional filters: name (partial match), status, classificationId, mealId."
	)
		  @RequestBody DishListDto dishListDto) {
		return ResponseEntity.ok(dishService.list(dishListDto));
	}

	@Operation(summary = "Find a dish info by ID")
	@GetMapping("/{id}")
	@ApiResponse(responseCode = "404", description = "Dish not found")
	public ResponseEntity<Dish> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(dishService.findById(id));
	}

	@Operation(summary = "Add a new dish")
	@ApiResponses(
		  @ApiResponse(responseCode = "409", description = "Conflict: Dish name already exists")
	)
	@PostMapping("/add")
	public ResponseEntity<?> add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
		  description = "Dish details. Required fields: name, price, status")
	                                   @RequestBody DishDto dishDto) {
		dishService.add(dishDto);
		return ResponseEntity.ok().build();
	}
}
