package com.atguigu.crm.daos;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.Customer;

public interface CustomerDeleteRepository {

	
	@Select("select * from customers where id = #{id}")
	Customer findOne(Integer id);

	@Update("update customers set state = #{state} where id = #{id}")
	void update(Customer customer);
	
}
