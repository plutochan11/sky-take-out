package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.exception.DishNotFoundException;
import com.pluto.exception.MealNotFoundException;
import com.pluto.mapper.MealDishMapper;
import com.pluto.mapper.MealMapper;
import com.pluto.model.Meal;
import com.pluto.model.MealDish;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;
import com.pluto.service.MealService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MealServiceImpl implements MealService {
	@Resource
	private MealMapper mealMapper;
	@Resource
	private MealDishMapper mealDishMapper;

	@Transactional
	@Override
	public void add(MealAddDto mealAddDto) {
		mealMapper.add(mealAddDto);
		Integer mealId = mealAddDto.getId();

		List<MealDish> mealDishes = new ArrayList<>();
		mealAddDto.getDishIdsAndQuantities().forEach((dishId, quantity) -> {
			MealDish mealDish = MealDish.builder()
				  .mealId(mealId)
				  .dishId(dishId)
				  .quantity(quantity)
				  .build();
			mealDishes.add(mealDish);
		});
		mealDishMapper.addBatch(mealDishes);
	}

	@Override
	public void delete(Integer id) {
		if (mealMapper.deleteById(id) == 0) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
	}

	@Override
	public void toggleStatus(Integer id) {
		Meal meal = mealMapper.findById(id);
		if (meal == null) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
		Integer newStatus = meal.getStatus() == 1 ? 0 : 1;
		mealMapper.updateStatus(id, newStatus);
	}

	@Override
	public Meal findById(Integer id) {
		Meal meal = mealMapper.findById(id);
		if (meal == null) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
		return meal;
	}

	@Override
	public PageInfo<Meal> findWithFilter(MealListDto mealListDto) {
		PageHelper.startPage(mealListDto.getPageNo(), mealListDto.getPageSize());
		List<Meal> meals = mealMapper.findWithFilter(mealListDto);
		return new PageInfo<>(meals);
	}

	@Override
	public List<Meal> findAll() {
		return mealMapper.findAll();
	}
}
