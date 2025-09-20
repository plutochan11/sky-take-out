package com.pluto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealDish {
	private Integer mealId;
	private Integer dishId;
	private Integer quantity;
}
