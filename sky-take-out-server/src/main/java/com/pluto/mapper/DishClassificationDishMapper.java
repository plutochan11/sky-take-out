package com.pluto.mapper;

import com.pluto.model.dto.DishAddDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishClassificationDishMapper {
	@Insert("""
	insert into dish_classification_dish (dish_classification_id, dish_id) 
	values (#{dishClassificationId}, #{dishId})
""")
	void add(Integer dishClassificationId, Integer dishId);

	/**
	 * Add new dishes to an existing classification
	 * @param classificationId, dishIds
	 */
	void addDishesToClassification(Integer classificationId, List<Integer> dishIds);
}
