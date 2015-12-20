package com.atguigu.crm.daos;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.SalesPlan;

public interface MakePlanMapper {
	
	
	
	
	/**
	 * @method 终止开发（开发失败）
	 */
	@Update("UPDATE sales_chances  SET status = 4  WHERE id = #{id}")
	public void stop(Integer id);
	
	
	/**
	 * 开发成功
	 */
	@Update("UPDATE sales_chances  SET status = 3  WHERE id = #{id}")
	public void finish(Integer id);
	
	
	/**
	 * 开发成功时插入字段
	 */
	@SelectKey(before=true,keyColumn="id",keyProperty="id",statement="select crm_seq.nextval from dual",resultType=long.class)
	@Insert("insert into contacts(id,name,tel,customer_id) values(#{id},#{name},#{tel},#{customer_id})")
	public void insertCon(Map<String, Object> map); 
	
	@SelectKey(before=true,keyColumn="id",keyProperty="id",statement="select crm_seq.nextval from dual",resultType=long.class)
	@Insert("insert into customers(id,name,no,state) values(#{id},#{name},#{no},#{state})")
	public void insertCust(Customer customer);
	
	
	/**
	 * 根据id查询saleChance
	 */
	@Select("SELECT s.id, source, cust_name, rate, title, "
			+ "contact, contact_tel, description, create_date, u.name as \"createBy.name\" ,"
			+ "u.name as \"designee.name\" "
			+ "FROM sales_chances s "
			+ "LEFT OUTER JOIN users u "
			+ "ON s.created_user_id = u.id "
			+ "WHERE s.id = #{id}")
	public SalesChance getSaleChanceById(@Param("id") Integer id);

	/**
	 * 为上个方法服务
	 * @param id
	 * @return
	 */
	@Select("SELECT id,todo,plan_date as \"date\",plan_result as \"result\" from sales_plan where chance_id=#{id}")
	public Set<SalesPlan> getListSalesPlanById(@Param("id") Integer id);
	
	
	@Insert("INSERT　INTO sales_plan(id,plan_date,todo,chance_id) VALUES(departments_seq.nextval,to_date(#{date},'yyyy/MM/dd'),#{todo},#{id})")
	public void insert(Map<String,Object> map);
	
	@Delete("DELETE from sales_plan where id=#{id}")
	public int delete(@Param("id") int id);
	
	@Update("UPDATE sales_plan SET todo=#{todo} where id=#{id}")
	public int updateById(@Param("todo")String todo,@Param("id") int id);
	
}
