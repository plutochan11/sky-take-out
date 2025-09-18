package com.pluto.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MealDishMapper {
	@Insert("""
	insert into meal_dish(meal_id, dish_id, quantity)
	    values(#{mealId}, #{dishId}, #{quantity})
""")
	void add(Integer mealId, Integer dishId, Integer quantity);
}
