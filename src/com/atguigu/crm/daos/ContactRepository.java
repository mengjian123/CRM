package com.atguigu.crm.daos;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import com.atuigu.crm.entity.Contact;

public interface ContactRepository {

	@SelectKey(before=true,keyColumn="id",keyProperty="id",statement="select crm_seq.nextval from dual",resultType=long.class)
	@Insert("insert into contacts(id,name,tel,customer_id) values(#{id},#{name},#{tel},#{customer_id})")
	public void save(Map<String, Object> map); 
	
}
