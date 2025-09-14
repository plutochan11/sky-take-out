package com.pluto.service;

import com.pluto.mapper.DishMapper;
import com.pluto.model.dto.DishDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService{
	@Resource
	private DishMapper dishMapper;

	@Override
	public void add(DishDto dishDto) {
		// Check if the dish name already exists
		dishMapper.add(dishDto);
	}
}
