package com.pluto.controller.management;

import com.pluto.model.dto.MealDishAddDto;
import com.pluto.service.MealDishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Meal Dish", description = "Meal Dish related APIs")
@RestController
@RequestMapping("/management/meal/dish")
public class MealDishController {
	@Resource
	private MealDishService mealDishService;

	@Operation(summary = "Add dishes to a meal")
	@ApiResponses({
		  @ApiResponse(responseCode = "200"),
		  @ApiResponse(responseCode = "404")
	})
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody MealDishAddDto dto) {
		mealDishService.add(dto);
		return ResponseEntity.ok().build();
	}
}
