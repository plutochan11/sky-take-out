package com.pluto.service;

import com.pluto.model.dto.DishDto;

public interface DishService {
	/**
	 * Add a new dish
	 * @param dishDto The dish info to be added
	 */
	void add(DishDto dishDto);

}
