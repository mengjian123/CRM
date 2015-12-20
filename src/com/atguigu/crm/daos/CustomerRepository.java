package com.atguigu.crm.daos;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.atuigu.crm.entity.Customer;

public interface CustomerRepository {

	@SelectKey(before=true,keyColumn="id",keyProperty="id",statement="select crm_seq.nextval from dual",resultType=long.class)
	@Insert("insert into customers(id,name,no,state) values(#{id},#{name},#{no},#{state})")
	public void save(Customer customer) ;

}
