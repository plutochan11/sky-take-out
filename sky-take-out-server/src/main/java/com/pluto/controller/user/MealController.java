package com.pluto.controller.user;

import com.pluto.model.Dish;
import com.pluto.model.Meal;
import com.pluto.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User Meal", description = "User Meal related APIs")
@RestController("userMealController")
@RequestMapping("/user/meals")
public class MealController {
	@Resource
	private MealService mealService;

	@Operation(summary = "Get all meals")
	@GetMapping
	public ResponseEntity<List<Meal>> getAll() {
		List<Meal> meals = mealService.getAllMeal();
		return ResponseEntity.ok(meals);
	}

	@Operation(summary = "Get dishes in a specific meal")
	@ApiResponses({@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")})
	@GetMapping("/{id}/dishes")
	public ResponseEntity<List<Dish>> getDishes(@PathVariable Integer id) {
		return ResponseEntity.ok(mealService.getDishesInMeal(id));
	}
}
