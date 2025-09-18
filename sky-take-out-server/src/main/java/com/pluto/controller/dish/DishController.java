package com.pluto.controller.dish;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Admin;
import com.pluto.model.Dish;
import com.pluto.model.dto.AdminListDto;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDto;
import com.pluto.model.dto.DishListDto;
import com.pluto.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/dish")
@Tag(name = "Dish", description = "Dish related APIs")
public class DishController {
	@Resource private DishService dishService;

	@Operation(summary = "Delete a dish by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		dishService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Toggle dish status (e.g., available/unavailable) by id")
	@GetMapping("/status/{id}")
	public ResponseEntity<?> toggleStatus(@PathVariable Integer id) {
		dishService.toggleStatus(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "List dishes with pagination")
	@ApiResponse(responseCode = "200")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		  description = "Required fields: pageNo, pageSize. Optional filters: name (partial match), status, classificationId, mealId."
	)
	@PostMapping("/list")
	public ResponseEntity<PageInfo<Dish>> listWithPaginationAndFilters(@RequestBody DishListDto dishListDto) {
		return ResponseEntity.ok(dishService.list(dishListDto));
	}

	@Operation(summary = "Find a dish info by ID")
	@ApiResponses(
		  {
			    @ApiResponse(responseCode = "200"),
			    @ApiResponse(responseCode = "404")
		  }
	)
	@GetMapping("/{id}")
	public ResponseEntity<Dish> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(dishService.findById(id));
	}

	@Operation(summary = "Add a new dish")
	@ApiResponses(
		  {
			    @ApiResponse(responseCode = "200"),
			    @ApiResponse(responseCode = "409",
				      content = @Content(examples = @ExampleObject(value = "Conflict: Dish name already exists")))
		  }
	)
	@PostMapping("/add")
	public ResponseEntity<?> add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
		  description = "Dish details. Required fields: name, price, status")
	                                   @RequestBody DishAddDto dto) {
		dishService.add(dto);
		return ResponseEntity.ok().build();
	}
}
