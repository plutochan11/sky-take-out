package com.pluto.service;

import com.pluto.mapper.DishClassificationMapper;
import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishClassificationServiceImpl implements DishClassificationService{
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
}
