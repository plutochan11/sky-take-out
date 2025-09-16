package com.pluto.controller.dish_classification;

import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.service.DishClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/dish/classification")
@Tag(name = "Dish Classification", description = "Dish Classification related APIs")
public class DishClassificationController {
	@Resource
	private DishClassificationService dishClassificationService;

	@Operation(summary = "Add a new dish classification")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody DishClassificationAddDto dishClassificationAddDto) {
		dishClassificationService.add(dishClassificationAddDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Return all dish classification")
	@GetMapping("/list")
	public ResponseEntity<List<DishClassification>> findAll() {
		return ResponseEntity.ok(dishClassificationService.findAll());
	}
}
