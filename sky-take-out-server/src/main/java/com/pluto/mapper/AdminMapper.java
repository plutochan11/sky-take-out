package com.pluto.mapper;

import com.pluto.model.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
	@Select("""
	select id, username, password, name, created_at, updated_at
	from admin
""")
	List<Admin> findAll();

	@Select("""
	select id, username, password, name, created_at, updated_at
	from admin
	where username = #{username}
""")
	Admin findOneByUsername(String username);

	/**
	 * Check if an admin with the given username exists
	 * @param username The admin username
	 * @return 1 if exists, 0 otherwise
	 */
	@Select("""
	  select count(0)
	  from admin
	  where username = #{username}
""")
	int existsByUsername(String username);

	/**
	 * Check if an admin with the given username and password exists
	 * @param username The admin username
	 * @param password The admin password
	 * @return 1 if exists, 0 otherwise
	 */
	@Select("""
	select count(0)
	from admin
	where username = #{username} and password = #{password}
""")
	int existsByUsernameAndPassword(String username, String password);

	@Insert("""
	insert into admin (username, password, name)
	values (#{username}, #{password}, #{name})
""")
	void add(Admin admin);


}
