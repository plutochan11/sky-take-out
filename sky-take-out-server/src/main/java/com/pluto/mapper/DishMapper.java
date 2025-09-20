package com.pluto.mapper;

import com.pluto.model.Dish;
import com.pluto.model.dto.DishDetailDto;
import com.pluto.model.dto.DishListDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {
	@Update("""
	update dish
	set classification_id = #{dishClassificationId}
	where id = #{id}
""")
	void updateClassificationIdById(Integer id, Integer dishClassificationId);

	void add(Dish dish);

	@Select("""
	select id, name, price, image, description, status, classification_id, 
	       meal_id, created_at, updated_at
	from dish
	where id = #{id}
""")
	Dish findById(Integer id);

	DishDetailDto findDetailById(Integer id);

	List<DishDetailDto> findDetailsWithFilters(DishListDto dishListDto);

	@Update("""
	update dish
	set status = #{status}
	where id = #{id}
""")
	void updateStatus(Dish dish);

	@Select("""
	select count(0) 
	from dish 
	where id = #{id}
""")
	Integer existsById(Integer id);

	@Delete("""
	delete from dish
	where id = #{id}
""")
	void delete(Integer id);
}
