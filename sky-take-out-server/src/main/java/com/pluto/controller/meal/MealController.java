package com.pluto.controller.meal;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Meal;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;
import com.pluto.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/meal")
@Tag(name = "Meal", description = "Meal related APIs")
public class MealController {

	@Resource
	private MealService mealService;

	@Operation(summary = "Delete a meal by ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404")
	})
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Integer id) {
		mealService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Toggle the availability status of a meal")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404")
	})
	@PatchMapping("/status/toggle")
	public ResponseEntity<?> toggleStatus(@RequestParam Integer id) {
		mealService.toggleStatus(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Add a new meal")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody MealAddDto mealAddDto) {
		mealService.add(mealAddDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Find a meal by ID")
	@GetMapping("/find")
	public ResponseEntity<Meal> findById(@RequestParam Integer id) {
		Meal meal = mealService.findById(id);
		return ResponseEntity.ok(meal);
	}

	@Operation(summary = "List meals with filters")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Meal>> findWithFilter(@RequestBody MealListDto mealListDto) {
		PageInfo<Meal> meals = mealService.findWithFilter(mealListDto);
		return ResponseEntity.ok(meals);
	}

	@Operation(summary = "List all meals")
	@GetMapping("/all")
	public ResponseEntity<List<Meal>> findAll() {
		List<Meal> meals = mealService.findAll();
		return ResponseEntity.ok(meals);
	}
}
