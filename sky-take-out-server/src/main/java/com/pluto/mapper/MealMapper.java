package com.pluto.mapper;

import com.pluto.model.Meal;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MealMapper {
	@Select("""
	select id, name, status, created_at, updated_at
	    from meal
	""")
	List<Meal> findAll();

	@Insert("""
	insert into meal(name, status)
	    values(#{name}, #{status})
	""")
	void add(MealAddDto mealAddDto);

	List<Meal> findWithFilter(MealListDto mealListDto);

	@Update("""
	update meal
	    set status = #{status}
	    where id = #{id}
	""")
	void updateStatus(Integer id, Integer status);

	@Select("""
	select id, name, status, created_at, updated_at
	    from meal
	    where id = #{id}
	""")
	Meal findById(Integer id);

	@Select("""
	select count(*)
	    from meal
	    where id = #{id}
	""")
	Integer existsById(Integer id);

	@Delete("""
	delete from meal
	    where id = #{id}
	""")
	void deleteById(Integer id);
}
