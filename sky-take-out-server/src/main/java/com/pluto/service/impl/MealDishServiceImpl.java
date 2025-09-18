package com.pluto.service.impl;

import com.pluto.mapper.MealDishMapper;
import com.pluto.model.dto.MealDishAddDto;
import com.pluto.service.MealDishService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MealDishServiceImpl implements MealDishService {
	@Resource
	private MealDishMapper mealDishMapper;

	@Override
	public void add(MealDishAddDto dto) {
		Integer mealId = dto.getMealId();
	}
}
