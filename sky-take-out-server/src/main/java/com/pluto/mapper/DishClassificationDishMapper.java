package com.pluto.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishClassificationDishMapper {
	@Insert("""
	insert into dish_classification_dish (dish_classification_id, dish_id) 
	values (#{dishClassificationId}, #{dishId})
""")
	void add(Integer dishClassificationId, Integer dishId);
}
