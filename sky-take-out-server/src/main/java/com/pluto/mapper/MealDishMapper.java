package com.pluto.mapper;

import com.pluto.model.MealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MealDishMapper {
	@Insert("""
	insert into meal_dish(meal_id, dish_id, quantity)
	    values(#{mealId}, #{dishId}, #{quantity})
""")
	void add(Integer mealId, Integer dishId, Integer quantity);

	void addBatch(List<MealDish> mealDishes);

	@Select("""
	select count(0) from meal_dish
	where dish_id = #{dishId}
""")
	int existsByDishId(Integer dishId);
}
