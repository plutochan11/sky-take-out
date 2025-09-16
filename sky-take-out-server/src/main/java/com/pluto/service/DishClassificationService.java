package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.model.dto.DishClassificationListDto;

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

	/**
	 * Return classification with pagination and filter
	 * @param dishClassificationListDto
	 * @return PageInfo<DishClassification>
	 */
	PageInfo<DishClassification> findWithPaginationAndFilter(DishClassificationListDto dishClassificationListDto);

	/**
	 * Toggle the availability status of a dish classification
	 * @param id
	 */
	void toggleStatus(Integer id);

	/**
	 * Delete a dish classification by ID
	 * @param id
	 */
	void delete(Integer id);
}
