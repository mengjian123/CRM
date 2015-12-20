package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.SerMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;

@Service
public class SerService {
	
	@Autowired
	private SerMapper serMapper;
	
	@Transactional
	public List<String> getCustomerService(){
		return serMapper.getType();
	}
	
	@Transactional
	public List<Customer> getCustomer(){
		return serMapper.getCustomer();
	}
	
	@Transactional
	public void save(String serviceType, String serviceTitle,
			String customerid, String serviceState, String createDate,
			String serviceRequest) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("serviceType", serviceType);
		map.put("serviceTitle", serviceTitle);
		map.put("customerid", customerid);
		map.put("serviceState", serviceState);
		map.put("createDate", createDate);
		map.put("serviceRequest", serviceRequest);
		
		serMapper.save(map);
	}
	
	
	@Transactional
	public Page<CustomerService> getPage(int pageNo,int pageSize,Map<String,Object> params){
		
		Page<CustomerService> page =new Page<>();
		//用前台传入的pageNo替换属性里 的pageNo
		page.setPageNo(pageNo);
		
		//1. 把 params 转为 PropertyFilter 的集合.
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		//2. 把 PropertyFilter 的集合再转为实际上可以传递给 myBatis 的 Map 类型的参数
		Map<String,Object> mtbatisParams = parseFiltersToMyBatisParams(filters);
		mtbatisParams.put("createdId", 24);
		//总记录数
		long totalAmount = serMapper.getTotalAmount(mtbatisParams);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		mtbatisParams.put("fromIndex", fromIndex);
		mtbatisParams.put("endIndex", endIndex);
		
		List<CustomerService> content = serMapper.getContent(mtbatisParams);
		page.setContent(content);
		

		
		
		

		
		return page;
	}
	
	
	/**
	 * 为上个方法做的
	 * @param filters
	 * @return
	 */
	private Map<String, Object> parseFiltersToMyBatisParams(
			List<PropertyFilter> filters) {
		Map<String,Object> map =new HashMap<String, Object>();
		
		for (PropertyFilter filter : filters) {
			String propertyName = filter.getPropertyName();
			MatchType matchType = filter.getMatchType();
			Class propertyType = filter.getPropertyType();
			Object propertyVal = filter.getPropertyVal();
			
			//把传入的字符串转为实际的目标类型
			propertyVal = ReflectionUtils.convertValue(propertyVal, propertyType);	
			
			//把值进行必要的操作
			switch(matchType){
			case LIKE:
					propertyVal = "%" + propertyVal + "%";
			}
			map.put(propertyName, propertyVal);
		}
		
		return map;
	}
	
	
	@Transactional
	public List<User> getUserList(){
		return serMapper.getUser();
	}
}
