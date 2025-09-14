package com.pluto.controller.dish;

import com.pluto.model.dto.DishDto;
import com.pluto.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/dish")
@Tag(name = "Dish", description = "Dish related APIs")
public class DishController {
	@Resource private DishService dishService;

	@Operation(summary = "Add a new dish")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody DishDto dishDto) {
		dishService.add(dishDto);
		return ResponseEntity.ok().build();
	}
}
