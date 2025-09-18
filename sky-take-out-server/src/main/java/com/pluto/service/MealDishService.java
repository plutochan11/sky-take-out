package com.pluto.service;

import com.pluto.model.dto.MealDishAddDto;

public interface MealDishService {
	/**
	 * Add dishes to a meal
	 * @param dto
	 */
	void add(MealDishAddDto dto);
}
