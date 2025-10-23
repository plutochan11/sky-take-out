package com.pluto.controller.management;

import com.github.pagehelper.PageInfo;
import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.model.dto.DishClassificationListDto;
import com.pluto.service.DishClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("managementDishClassificationController")
@RequestMapping("/management/dish_classifications")
@Tag(name = "Management Dish Classification", description = "Dish Classification related APIs")
public class DishClassificationController {
	@Resource
	private DishClassificationService dishClassificationService;
	@Resource
	private RedisTemplate redisTemplate;

	@Operation(summary = "Add a dish classification (without dishes)")
	@PostMapping
	public ResponseEntity<?> add(@RequestBody DishClassificationAddDto dishClassificationAddDto) {
		dishClassificationService.add(dishClassificationAddDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Add new dishes to an existing classification")
	@PutMapping("/add/dishes")
	public ResponseEntity<?> addDishes(@RequestBody DishClassificationAddDto dto) {
		dishClassificationService.addDishes(dto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get all dish classifications")
	@GetMapping("/list")
	public ResponseEntity<List<DishClassification>> getAll() {
		return ResponseEntity.ok(dishClassificationService.findAll());
	}

	@Operation(summary = "Get dish classifications with pagination and filters (optional)")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<DishClassification>> get(
		  @RequestBody DishClassificationListDto dishClassificationListDto) {
		return ResponseEntity.ok(dishClassificationService.findWithPaginationAndFilter(dishClassificationListDto));
	}

	@Operation(summary = "Update the availability status of a dish classification")
	@ApiResponses({
		  @ApiResponse(responseCode = "200"),
		  @ApiResponse(responseCode = "404")
	})
	@PatchMapping("/status/toggle")
	public ResponseEntity<?> updateStatus(@RequestParam Integer id) {
		dishClassificationService.toggleStatus(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a dish classification by ID")
	@ApiResponses({
		  @ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")
	})
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Integer id) {
		dishClassificationService.delete(id);
		return ResponseEntity.ok().build();
	}
}
