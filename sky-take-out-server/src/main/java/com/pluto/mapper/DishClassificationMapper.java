package com.pluto.mapper;

import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishClassificationMapper {
	@Select("""
	select id, name, status, created_at, updated_at
	    from dish_classification
""")
	List<DishClassification> findAll();

	@Insert("""
	insert into dish_classification(name, status)
	    values(#{name}, #{status})
""")
	void add(DishClassificationAddDto dishClassificationAddDto);
}
