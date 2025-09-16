package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealDishAddDto {
	private Integer mealId;
	private List<Integer> dishIds;
}
