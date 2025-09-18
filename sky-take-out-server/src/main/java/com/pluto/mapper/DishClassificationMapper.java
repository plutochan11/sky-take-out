package com.pluto.mapper;

import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.model.dto.DishClassificationListDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishClassificationMapper {
	@Select("""
	select id, name, status, created_at, updated_at
	    from dish_classification
""")
	List<DishClassification> findAll();

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("""
	insert into dish_classification(name, status)
	    values(#{name}, #{status})
""")
	void add(DishClassificationAddDto dishClassificationAddDto);

	List<DishClassification> findWithFilter(DishClassificationListDto dishClassificationListDto);

	@Update("""
	update dish_classification
	    set status = #{status}
	    where id = #{id}
""")
	void updateStatus(Integer id, Integer status);

	@Select("""
	select id, name, status, created_at, updated_at
	    from dish_classification
	    where id = #{id}
""")
	DishClassification findById(Integer id);

	@Select("""
	select count(*)
	    from dish_classification
	    where id = #{id}
""")
	Integer existsById(Integer id);

	@Delete("""
	delete from dish_classification
	    where id = #{id}
""")
	Integer deleteById(Integer id);
}
