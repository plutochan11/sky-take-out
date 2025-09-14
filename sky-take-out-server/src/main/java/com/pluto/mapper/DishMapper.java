package com.pluto.mapper;

import com.pluto.model.dto.DishDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper {
	void add(DishDto dishDto);
}
