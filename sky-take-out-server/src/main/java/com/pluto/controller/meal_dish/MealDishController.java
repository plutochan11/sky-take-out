package com.pluto.controller.meal_dish;

import com.pluto.model.dto.MealDishAddDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Meal Dish", description = "Meal Dish related APIs")
@RestController
@RequestMapping("/management/meal/dish")
public class MealDishController {
	@Operation(summary = "Add dishes to a meal")
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody MealDishAddDto dto) {
		// TODO implement the logic to add dishes to a meal
	}
}
