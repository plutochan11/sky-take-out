package com.pluto.controller.management;

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
@RequestMapping("/management/meals")
@Tag(name = "Management Meal", description = "Meal related APIs")
public class MealController {
	@Resource
	private MealService mealService;

	@Operation(summary = "Add a new meal")
	@PostMapping
	public ResponseEntity<?> add(@RequestBody MealAddDto mealAddDto) {
		mealService.addMeal(mealAddDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get a meal by ID")
	@GetMapping("/find")
	public ResponseEntity<Meal> get(@RequestParam Integer id) {
		Meal meal = mealService.getMealsWithPagination(id);
		return ResponseEntity.ok(meal);
	}

	@Operation(summary = "Get meals with filters")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Meal>> get(@RequestBody MealListDto mealListDto) {
		PageInfo<Meal> meals = mealService.getMealsWithPagination(mealListDto);
		return ResponseEntity.ok(meals);
	}

	@Operation(summary = "Get all meals")
	@GetMapping
	public ResponseEntity<List<Meal>> getAll() {
		List<Meal> meals = mealService.getAllMeal();
		return ResponseEntity.ok(meals);
	}

	@Operation(summary = "Update the availability status of a meal")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404")
	})
	@PatchMapping("/status/toggle")
	public ResponseEntity<?> updateStatus(@RequestParam Integer id) {
		mealService.updateStatus(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a meal by ID")
	@ApiResponses({
		  @ApiResponse(responseCode = "200"),
		  @ApiResponse(responseCode = "404")
	})
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Integer id) {
		mealService.deleteMeal(id);
		return ResponseEntity.ok().build();
	}
}
