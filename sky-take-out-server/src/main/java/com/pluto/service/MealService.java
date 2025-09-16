package com.pluto.service;

import com.github.pagehelper.PageInfo;
import com.pluto.model.Meal;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;

import java.util.List;

public interface MealService {
	void add(MealAddDto mealAddDto);

	void delete(Integer id);

	void toggleStatus(Integer id);

	Meal findById(Integer id);

	PageInfo<Meal> findWithFilter(MealListDto mealListDto);

	List<Meal> findAll();
}
