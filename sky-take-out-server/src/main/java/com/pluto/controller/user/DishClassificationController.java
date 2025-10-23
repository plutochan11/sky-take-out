package com.pluto.controller.user;

import com.pluto.model.Dish;
import com.pluto.model.DishClassification;
import com.pluto.service.DishClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(description = "User Dish Classification Related APIs", name = "User Dish Classification")
@RestController("userDishClassificationController")
@RequestMapping("/user/dish-classification")
public class DishClassificationController {
	@Resource
	private DishClassificationService dishClassificationService;

	@Operation(summary = "Get all dish classifications")
	@GetMapping("/list")
	public ResponseEntity<List<DishClassification>> getAll() {
		return ResponseEntity.ok(dishClassificationService.findAll());
	}

	@Operation(summary = "Get all dishes in a specific classification")
	@ApiResponses({@ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")})
	@GetMapping("/{id}/dishes")
	public ResponseEntity<List<Dish>> getDishes(@PathVariable Integer id) {
		List<Dish> dishes = dishClassificationService.getDishes(id);
		return ResponseEntity.ok(dishes);
	}
}
