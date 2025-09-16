package com.pluto.controller.dish_classification;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/dish/classification")
@Tag(name = "Dish Classification", description = "Dish Classification related APIs")
public class DishClassificationController {
	@Resource
	private DishClassificationService dishClassificationService;

	@Operation(summary = "Delete a dish classification by ID")
	@ApiResponses(
		  {
			    @ApiResponse(responseCode = "200"),
			    @ApiResponse(responseCode = "404")
		  }
	)
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Integer id) {
		dishClassificationService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Toggle the availability status of a dish classification")
	@ApiResponses(
		  {
			    @ApiResponse(responseCode = "200"),
			    @ApiResponse(responseCode = "404")
		  }
	)
	@PatchMapping("/status/toggle")
	public ResponseEntity<?> toggleStatus(@RequestParam Integer id) {
		dishClassificationService.toggleStatus(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Add a new dish classification")
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody DishClassificationAddDto dishClassificationAddDto) {
		dishClassificationService.add(dishClassificationAddDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Retrieve dish classifications with pagination and optional filters")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<DishClassification>> findWithPaginationAndFilter(
		  @RequestBody DishClassificationListDto dishClassificationListDto) {
		return ResponseEntity.ok(dishClassificationService.findWithPaginationAndFilter(dishClassificationListDto));
	}

	@Operation(summary = "Retrieve all dish classification")
	@GetMapping("/list")
	public ResponseEntity<List<DishClassification>> findAll() {
		return ResponseEntity.ok(dishClassificationService.findAll());
	}
}
