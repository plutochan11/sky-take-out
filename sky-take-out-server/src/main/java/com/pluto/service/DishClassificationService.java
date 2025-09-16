package com.pluto.service;

import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;

import java.util.List;

public interface DishClassificationService {
	/**
	 * Return all classification
	 * @return List<DishClassification>
	 */
	List<DishClassification> findAll();

	/**
	 * Add a new classification
	 * @param dishClassificationAddDto
	 */
	void add(DishClassificationAddDto dishClassificationAddDto);
}
