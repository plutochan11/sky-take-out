package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.exception.DishNotFoundException;
import com.pluto.mapper.DishMapper;
import com.pluto.model.Dish;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDto;
import com.pluto.model.dto.DishListDto;
import com.pluto.service.DishService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {
	@Resource
	private DishMapper dishMapper;
	@Resource
	private AliyunOssFileUploadService fileUploadService;

	@Override
	public void add(DishAddDto dto) {
		String imageUrl = fileUploadService.upload(dto.getImage());

		// Check if the dish name already exists
		dishMapper.add(dishDto);
	}

	@Override
	public Dish findById(Integer id) {
		Dish dish = dishMapper.findById(id);
		if (dish != null) {
			return dish;
		} else {
			throw new DishNotFoundException("Dish with ID " + id + " not found.");
		}
	}

	@Override
	public PageInfo<Dish> list(DishListDto dishListDto) {
		PageHelper.startPage(dishListDto.getPageNo(), dishListDto.getPageSize());
		return new PageInfo<>(dishMapper.findByConditions(dishListDto));
	}

	@Override
	public void toggleStatus(Integer id) {
		Dish dish = dishMapper.findById(id);
		if (dish == null) {
			throw new DishNotFoundException("Dish with ID " + id + " not found.");
		}
		Short newStatus = (short) ((dish.getStatus() == 1) ? 0 : 1);
		dish.setStatus(newStatus);
		dishMapper.updateStatus(dish);
	}

	@Override
	public void delete(Integer id) {
		// Verify if the dish exists
		if (dishMapper.existsById(id) == 1) {
			dishMapper.delete(id);
		} else {
			throw new DishNotFoundException("Dish with ID " + id + " not found.");
		}
	}
}
