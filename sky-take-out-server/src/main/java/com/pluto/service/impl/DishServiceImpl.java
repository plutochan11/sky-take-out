package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.exception.DishNotFoundException;
import com.pluto.mapper.DishMapper;
import com.pluto.model.Dish;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDetailDto;
import com.pluto.model.dto.DishListDto;
import com.pluto.service.DishService;
import com.pluto.util.DtoConverter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {
	@Resource
	private DishMapper dishMapper;
	@Resource
	private AliyunOssFileUploadService fileUploadService;
	@Resource
	private DtoConverter dtoConverter;

	@Override
	public void add(DishAddDto dto) throws NoSuchMethodException {
		String imageUrl = fileUploadService.upload(dto.getImage());
		dto.setImage(null); // Clear the MultipartFile to avoid serialization issues
		Dish dish = dtoConverter.dtoToEntity(dto, Dish.class);
		dish.setImage(imageUrl);
		// Check if the dish name already exists
		dishMapper.add(dish);
	}

	@Override
	public DishDetailDto findDetailById(Integer id) {
		DishDetailDto dto = dishMapper.findDetailById(id);
		if (Objects.nonNull(dto)) {
			return dto;
		} else {
			throw new DishNotFoundException("Dish with ID " + id + " not found.");
		}
	}

	@Override
	public PageInfo<DishDetailDto> listWithPaginationAndFilters(DishListDto dishListDto) {
		PageHelper.startPage(dishListDto.getPageNo(), dishListDto.getPageSize());
		return new PageInfo<>(dishMapper.findDetailsWithFilters(dishListDto));
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
