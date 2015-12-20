package com.atguigu.crm.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.Customer;

public interface CustomerPageMapper {
	
	long getTotalAmount(Map<String, Object> params);
	
	List<Customer> getContent(Map<String, Object> params);
	
	@Select("select distinct region from customers")
	List<String>getRegions();
	
	@Select("select distinct customer_level from customers")
	List<String>getCustomerLevel();
}
