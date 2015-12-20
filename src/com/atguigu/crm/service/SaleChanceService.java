package com.atguigu.crm.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.SaleChanceMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;
import com.atuigu.crm.entity.SalesChance;

@Service
public class SaleChanceService {
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;
	
	@Transactional
	public Page<SalesChance> getPage(int pageNo,int pageSize,Map<String,Object> params){
		
		Page<SalesChance> page =new Page<>();
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
		long totalAmount = saleChanceMapper.getTotalAmount(mtbatisParams);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		mtbatisParams.put("fromIndex", fromIndex);
		mtbatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = saleChanceMapper.getContent(mtbatisParams);
		page.setContent(content);

		return page;
	}
	
	@Transactional
	public Page<SalesChance> getPagedPlanList(int pageNo,int pageSize,Map<String,Object> params, Long loginUserId){
		
		Page<SalesChance> page =new Page<>();
		//用前台传入的pageNo替换属性里 的pageNo
		page.setPageNo(pageNo);
		
		//1. 把 params 转为 PropertyFilter 的集合.
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		//2. 把 PropertyFilter 的集合再转为实际上可以传递给 myBatis 的 Map 类型的参数
		Map<String,Object> mtbatisParams = parseFiltersToMyBatisParams(filters);
		Set status = new HashSet<>();
		status.add(2);
		status.add(3);
		status.add(4);
		//List<Integer> status =Arrays.asList(2,2,3,4);
		mtbatisParams.put("status", status);
		mtbatisParams.put("loginUserId", loginUserId);
		//总记录数
		long totalAmount = saleChanceMapper.getPagedPlanTotalAmount(mtbatisParams);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		mtbatisParams.put("fromIndex", fromIndex);
		mtbatisParams.put("endIndex", endIndex);
		
		List<SalesChance> content = saleChanceMapper.getPagedPlanContent(mtbatisParams);
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

	/**
	 * 增加对象
	 * @param salesChance
	 */
	@Transactional
	public void save(SalesChance salesChance){
		
		salesChance.setStatus(1);
		
		saleChanceMapper.save(salesChance);
		

	}
	
	/**
	 * 修改对象
	 */
	@Transactional
	public void update(SalesChance salesChance){
		saleChanceMapper.update(salesChance);
	}
	
	/**
	 * 根据id查找对象
	 */
	public SalesChance getSalesChanceById(Integer id){
		return saleChanceMapper.getSalesChanceById(id);
	}
	
	
	/**
	 * 删除对象
	 */
	public void delete(Integer id){
		saleChanceMapper.delete(id);
	}
	
	/**
	 * 指派
	 */
	@Transactional
	public void dispatch(SalesChance salesChance){
		saleChanceMapper.dispatch(salesChance);
	}
}
