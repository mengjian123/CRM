package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.CustomerPageMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.Customer;

@Service
public class PagedCustomerService {

	@Autowired
	private CustomerPageMapper customerPageMapper;
	
	@Transactional
	public Page<Customer> getPage(int pageNo,int pageSize,Map<String,Object> params){
		
		Page<Customer> page =new Page<>();
		//用前台传入的pageNo替换属性里 的pageNo
		page.setPageNo(pageNo);
		
		//1. 把 params 转为 PropertyFilter 的集合.
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		//2. 把 PropertyFilter 的集合再转为实际上可以传递给 myBatis 的 Map 类型的参数
		Map<String,Object> mtbatisParams = parseFiltersToMyBatisParams(filters);
	/*	//List<Integer> status =Arrays.asList(1);
		Set status = new HashSet<>();
		status.add(1);
		mtbatisParams.put("status", status);*/
		mtbatisParams.put("status", 1);
		//总记录数
		long totalAmount = customerPageMapper.getTotalAmount(mtbatisParams);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		mtbatisParams.put("fromIndex", fromIndex);
		mtbatisParams.put("endIndex", endIndex);
		
		List<Customer> content = customerPageMapper.getContent(mtbatisParams);
		page.setContent(content);

		return page;
	}
	
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
	public List<String> getRegions(){
		List<String> regions = customerPageMapper.getRegions();
		return regions;
	}
	
	@Transactional
	public List<String> getCustomerLevel(){
		List<String> customerLevel = customerPageMapper.getCustomerLevel();
		return customerLevel;
	}
}
