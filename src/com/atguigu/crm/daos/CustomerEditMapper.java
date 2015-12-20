package com.atguigu.crm.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

public interface CustomerEditMapper {

	Customer getCustomerInfoById(Map map);
	
	@Select("select distinct region from customers ")
	List<String> getRegions();
	
	@Select(" select distinct customer_level from customers")
	List<String>getCustomerLevels();
	
	@Select("select distinct satify from customers")
	List<String>getSatisfy();
	
	List<Contact>getManager();
	
	@Select("select distinct credit from customers")
	List<String>getCredit();
	
	void updateCustomerById(Map map);
	
}
