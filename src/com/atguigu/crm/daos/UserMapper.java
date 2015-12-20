package com.atguigu.crm.daos;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.User;


public interface UserMapper {
	
	@Select("SELECT id, name, password, enabled FROM users WHERE name = #{name}")
	public User getByName(@Param("name") String name);
	
	@Select("SELECT id, name, password, enabled FROM users")
	public List<User> getAll();
}
