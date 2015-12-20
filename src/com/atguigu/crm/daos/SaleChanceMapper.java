package com.atguigu.crm.daos;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atuigu.crm.entity.SalesChance;

public interface SaleChanceMapper {

	
	/**
	 * 开发成功
	 * @param id
	 */
	@Update("UPDATE sales_chances  SET status = 3  WHERE id = #{id}")
	void finish(Integer id);
	
	// 分页操作
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	long getTotalAmount(Map<String, Object> params);

	long getPagedPlanTotalAmount(Map<String, Object> params);

	/**
	 * 因为是得带条件的了，所以必须写xml配置了
	 * 
	 * @param params
	 * @return
	 */
	// 获取当前页面的 content
	List<SalesChance> getContent(Map<String, Object> params);

	List<SalesChance> getPagedPlanContent(Map<String, Object> params);

	/**
	 * 存储对象
	 * 
	 * @param salesChance
	 */
	@Insert("INSERT INTO sales_chances(id,source, cust_name, rate, title,"
			+ "contact, contact_tel, description, created_user_id, create_date,status)"
			+ "VALUES(departments_seq.nextval,#{source},#{custName},#{rate},#{title},#{contact},#{contactTel}"
			+ ",#{description},#{createBy.id},#{createDate},#{status})")
	void save(SalesChance salesChance);

	/**
	 * 修改对象
	 */
	@Update("update sales_chances set source=#{source},cust_name=#{custName},rate=#{rate},title=#{title},"
			+ "contact=#{contact},contact_tel=#{contactTel},description=#{description}"
			+ " where id=#{id}")
	void update(SalesChance salesChance);

	/**
	 * 指派用户时进行修改操作
	 */
	@Update("update sales_chances set status=#{status},designee_date=#{designeeDate},designee_id=#{designee.id}"
			+ "	where id=#{id}")
	void dispatch(SalesChance salesChance);

	/**
	 * 删除对象
	 */
	@Delete("delete from sales_chances where id=#{id}")
	void delete(@Param("id") Integer id);

	/**
	 * 级联查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT s.id, source, cust_name, rate, title, "
			+ "contact, contact_tel, description, create_date, u.name as \"createBy.name\" , ur.name as \"designee.name\" "
			+ "FROM sales_chances s " + "LEFT OUTER JOIN users u "
			+ "ON s.created_user_id = u.id " + "LEFT OUTER JOIN users ur "
			+ "ON s.designee_id=ur.id " + "WHERE s.id = #{id}")
	SalesChance getSalesChanceById(@Param("id") Integer id);

	
}
