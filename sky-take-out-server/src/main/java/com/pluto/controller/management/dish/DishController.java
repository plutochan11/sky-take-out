package com.pluto.controller.management.dish;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Dish;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDetailDto;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/dish")
@Tag(name = "Dish", description = "Dish related APIs")
public class DishController {
	@Resource private DishService dishService;

	@Operation(summary = "List dishes with pagination and conditions")
	@ApiResponse(responseCode = "200")
	@PostMapping("/list")
	public ResponseEntity<PageInfo<DishDetailDto>> listWithPaginationAndFilters(
		  @RequestBody DishListDto dishListDto) {
		return ResponseEntity.ok(dishService.listWithPaginationAndFilters(dishListDto));
	}

	@Operation(summary = "Find dish details by ID")
	@ApiResponses({
		  @ApiResponse(responseCode = "200"), @ApiResponse(responseCode = "404")
	})
	@GetMapping("/{id}")
	public ResponseEntity<DishDetailDto> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(dishService.findDetailById(id));
	}

	@Operation(summary = "Add a new dish")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
		  description = "Dish details. Required fields: name, price, status",
		  content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
			    schema = @Schema(implementation = DishAddDto.class)
		  )
	)
	@ApiResponses({
		  @ApiResponse(responseCode = "200"),
		  @ApiResponse(responseCode = "409", content = @Content(
					examples = @ExampleObject(value = "Conflict: Dish name already exists")
			    ))
	})
	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@ModelAttribute DishAddDto dto) throws NoSuchMethodException {
		dishService.add(dto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Update a dish")
	@PatchMapping
	public ResponseEntity<?> update(@RequestBody Dish dish) {
		dishService.update(dish);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Toggle dish status (e.g., available/unavailable) by IDs")
	@PatchMapping("/status")
	public ResponseEntity<?> toggleStatus(@RequestParam List<Integer> ids) {
		dishService.toggleStatus(ids);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a dish by IDs")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		dishService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete dishes by IDs")
	@DeleteMapping()
	public ResponseEntity<?> deleteByIds(@RequestParam List<Integer> ids) {
		dishService.deleteByIds(ids);
		return ResponseEntity.ok().build();
	}
}
