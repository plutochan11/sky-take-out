package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.exception.MealNotFoundException;
import com.pluto.mapper.MealMapper;
import com.pluto.model.Meal;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;
import com.pluto.service.MealService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MealServiceImpl implements MealService {
	@Resource
	private MealMapper mealMapper;

	@Override
	public void add(MealAddDto mealAddDto) {
		mealMapper.add(mealAddDto);
	}

	@Override
	public void delete(Integer id) {
		if (mealMapper.existsById(id) == 1) {
			mealMapper.deleteById(id);
		} else {
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
