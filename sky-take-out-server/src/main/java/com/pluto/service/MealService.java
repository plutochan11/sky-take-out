package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Dish;
import com.pluto.model.Meal;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;

import java.util.List;

public interface MealService {
	void addMeal(MealAddDto mealAddDto);

	void deleteMeal(Integer id);

	void updateStatus(Integer id);

	Meal getMealsWithPagination(Integer id);

	PageInfo<Meal> getMealsWithPagination(MealListDto mealListDto);

	List<Meal> getAllMeal();

	/**
	 * Get dishes associated with the specified meal
	 * @param id The meal ID
	 * @return Dishes associated
	 */
	List<Dish> getDishesInMeal(Integer id);
}
