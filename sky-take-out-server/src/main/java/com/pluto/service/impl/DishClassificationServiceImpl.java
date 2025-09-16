package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.exception.DishClassificationNotFoundException;
import com.pluto.mapper.DishClassificationMapper;
import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.model.dto.DishClassificationListDto;
import com.pluto.service.DishClassificationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishClassificationServiceImpl implements DishClassificationService {
	@Resource
	private DishClassificationMapper dishClassificationMapper;

	@Override
	public List<DishClassification> findAll() {
		return dishClassificationMapper.findAll();
	}

	@Override
	public void add(DishClassificationAddDto dishClassificationAddDto) {
		dishClassificationMapper.add(dishClassificationAddDto);
	}

	@Override
	public PageInfo<DishClassification> findWithPaginationAndFilter(
		  DishClassificationListDto dishClassificationListDto) {
		PageHelper.startPage(dishClassificationListDto.getPageNo(),
			  dishClassificationListDto.getPageSize());
		return new PageInfo<>(dishClassificationMapper
			  .findWithFilter(dishClassificationListDto));
	}

	@Override
	public void toggleStatus(Integer id) {
		DishClassification dishClassification = dishClassificationMapper.findById(id);
		if (dishClassification == null) {
			throw new DishClassificationNotFoundException(
				  "Dish classification with ID " + id + " not found.");
		}
		if (dishClassification.getStatus() == 1) {
			dishClassificationMapper.updateStatus(id, 0);
		} else if (dishClassification.getStatus() == 0) {
			dishClassificationMapper.updateStatus(id, 1);
		} else {
			throw new IllegalStateException("Invalid status value: " +
				  dishClassification.getStatus() +
				  " for dish classification with ID " + id);
		}
	}

	@Override
	public void delete(Integer id) {
		if (dishClassificationMapper.existsById(id) == 1) {
			dishClassificationMapper.deleteById(id);
		} else {
			throw new DishClassificationNotFoundException(
				  "Dish classification with ID " + id + " not found.");
		}
	}
}
