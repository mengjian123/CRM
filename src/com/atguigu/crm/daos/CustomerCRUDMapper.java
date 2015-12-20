package com.atguigu.crm.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

public interface CustomerCRUDMapper {
	
	/**
	 * 查询总记录数
	 */
	@Select("SELECT count(id) from contacts WHERE customer_id =#{id}")
	public long getTotalAmount(Map<String,Object> map);
	
	
	
	/**
	 * 查询这个list
	 */
	public List<Contact> getContont(Map<String,Object> map);
	
	
	/**
	 * 查客户的
	 */
	@Select("select id,name from customers where id=#{id}")
	public Customer getCustomerById(@Param("id") Integer id);


	/**
	 * 添加对象
	 * @param map
	 */
	@Insert("INSERT INTO contacts (id,name,sex,position,tel,mobile,memo,customer_id)"
			+ "VALUES (departments_seq.nextval,#{name},#{sex},#{position},#{tel},#{mobile},#{memo},#{customerid})")
	public void save(Map<String, Object> map);


	@Delete("DELETE from contacts where id=#{id}")
	public void delete(@Param("id") Integer id);


	/**
	 * 根据id查询
	 * @param contactid
	 * @return
	 */
	@Select("SELECT id,name,sex,position,tel,mobile,memo from contacts where id=#{id}")
	public Contact getContactById(@Param("id") Integer id);


	/**
	 * 修改的
	 * @param con
	 */
	@Update("update contacts SET name=#{name},sex=#{sex},position=#{position},tel=#{tel},"
			+ "mobile=#{mobile},memo=#{memo} where id=#{id}")
	public void update(Contact con);
}
