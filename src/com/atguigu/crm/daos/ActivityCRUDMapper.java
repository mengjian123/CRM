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
import com.atuigu.crm.entity.CustomerActivity;


public interface ActivityCRUDMapper {

	/**
	 * 查询这个list
	 */
	public List<CustomerActivity> getContont(Map<String,Object> map);
	
	
	/**
	 * 查询总记录数
	 */
	@Select("SELECT count(id) from CUSTOMER_ACTIVITIES WHERE customer_id =#{id}")
	public long getTotalAmount(Map<String,Object> map);
	
	
	/**
	 * 查客户的
	 */
	@Select("select id,name from customers where id=#{id}")
	public Customer getCustomerById(@Param("id") Integer id);

	
	/**
	 * 添加对象
	 * @param map
	 */
	@Insert("INSERT INTO CUSTOMER_ACTIVITIES (id,ACTIVITY_DATE,place,title,description,customer_id)"
			+ "VALUES (departments_seq.nextval,to_date(#{date},'yyyy/MM/dd'),#{place},#{title},#{description},#{customerid})")
	public void save(Map<String, Object> map);
	
	/**
	 * 删除对象
	 */
	@Delete("DELETE from CUSTOMER_ACTIVITIES where id=#{id}")
	public void delete(@Param("id") Integer id);
	
	
	/**
	 * 根据id查询
	 * @param contactid
	 * @return
	 */
	@Select("SELECT id,ACTIVITY_DATE as \"date\",place,title,customer_id as \"customerid\",description from CUSTOMER_ACTIVITIES where id=#{id}")
	public CustomerActivity getActivityById(@Param("id") Integer id);


	/**
	 * 修改的
	 * @param con
	 */
	public void update(CustomerActivity ca);
}
