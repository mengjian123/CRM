package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.ActivityCRUDMapper;
import com.atguigu.crm.orm.Page;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerActivity;

@Service
public class ActivityCRUDService {
	
	@Autowired
	private ActivityCRUDMapper activityCRUDMapper;
	
	@Transactional
	public Page<CustomerActivity> getPage(int pageNo,int pageSize,Map<String,Object> params){
		
		Page<CustomerActivity> page =new Page<>();
		//用前台传入的pageNo替换属性里 的pageNo
		page.setPageNo(pageNo);

		//总记录数
		long totalAmount = activityCRUDMapper.getTotalAmount(params);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<CustomerActivity> content = activityCRUDMapper.getContont(params);
		page.setContent(content);

		return page;
	}
	
	
	@Transactional
	public Customer getCustomerById(Integer id){
		return activityCRUDMapper.getCustomerById(id);
	}

	
	@Transactional
	public void save(String date, String place, String title, String description,String customerid) {
			Map<String,Object> map =new HashMap<String, Object>();
			map.put("date", date);
			map.put("place", place);
			map.put("title", title);
			map.put("description", description);
			map.put("customerid", customerid);

			
			activityCRUDMapper.save(map);
		
	}
	
	
	@Transactional
	public void delete(Integer id) {
		activityCRUDMapper.delete(id);
	}
	
	
	/**
	 * 根据id在数据库中查
	 * @param contactid
	 * @return
	 */
	@Transactional
	public CustomerActivity getActivityById(Integer contactid) {
		return activityCRUDMapper.getActivityById(contactid);
	}

	/**
	 * 
	 * @param con
	 */
	@Transactional
	public void update(CustomerActivity ca) {
		activityCRUDMapper.update(ca);
	}
	
	
	
}
