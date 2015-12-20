package com.atguigu.crm.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;


public interface SerMapper {
	
	@Select("select service_type as \"serviceType\" from customer_services")
	public List<String> getType();
	
	public List<Customer> getCustomer();

	/**
	 * 存储的方法
	 * @param serviceType
	 * @param serviceTitle
	 * @param customerid
	 * @param serviceState
	 * @param createDate
	 * @param serviceRequest
	 */
	@Insert("INSERT INTO customer_services "
			+"(id,create_date,service_request,service_state,service_title,service_type,customer_id)" 
			+"values (departments_seq.nextval,to_date(#{createDate},'yyyy/MM/dd'),#{serviceRequest},"
			+ "#{serviceState},#{serviceTitle},#{serviceType},#{customerid})")
	public void save(Map<String,Object> map);
	
	/**
	 * 因为是得带条件的了，所以必须写xml配置了
	 * 
	 * @param params
	 * @return
	 */
	// 获取当前页面的 content
	List<CustomerService> getContent(Map<String, Object> params);

	
	// 分页操作
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	long getTotalAmount(Map<String, Object> params);
	
	
	/**
	 *查user
	 */
	@Select("SELECT id,name from Users")
	List<User> getUser();

	
	/**
	 * 查customer
	 */
	@Select("SELECT id,name from customers where id=#{id}")
	public Customer getCustomerById(@Param("id") Integer id);
	
	
	/**
	 * 查user
	 */
	@Select("SELECT id,name from USERS where id=#{id}")
	public User getUserById(@Param("id") Integer id);

}
