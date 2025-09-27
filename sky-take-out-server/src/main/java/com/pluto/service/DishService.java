package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDetailDto;
import com.pluto.model.dto.DishListDto;

import java.util.List;

public interface DishService {
	/**
	 * Add a new dish
	 * @param dishDto The dish info to be added
	 */
	void add(DishAddDto dishDto) throws NoSuchMethodException;

	/**
	 * Find a dish by its ID
	 * @param id The dish id
	 * @return The dish info
	 */
	DishDetailDto findDetailById(Integer id);

	/**
	 * List dishes with pagination and optional filters
	 *
	 * @param dishListDto The filter and pagination info
	 * @return Paginated list of dishes
	 */
	PageInfo<DishDetailDto> listWithPaginationAndFilters(DishListDto dishListDto);

	/**
	 * Toggle the status of a dish (e.g., available/unavailable)
	 * @param id The dish id
	 */
	void toggleStatus(Integer id);

	/**
	 * Delete a dish by its ID
	 * @param id The dish id
	 */
	void deleteById(Integer id);

	/**
	 * Batch delete dishes by their IDs
	 * @param ids The list of dish ids to be deleted
	 */
	void deleteByIds(List<Integer> ids);
}
