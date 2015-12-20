package com.atguigu.crm.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.MakePlanMapper;
import com.atguigu.crm.daos.SaleChanceMapper;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.SalesPlan;

@Service
public class MakePlanService {
	
	@Autowired
	private MakePlanMapper makePlanMapper;

	
	@Autowired
	private SaleChanceMapper saleChanceMapper;
	
	/**
	 * @method 开发失败
	 */
	@Transactional
	public void stop(Integer id){
		makePlanMapper.stop(id);
	}
	
	/**
	 * 开发成功
	 */
	@Transactional
	public void finish(Integer id){
		makePlanMapper.finish(id);
	}
	
	/**
	 * 开发成功时插入的字段
	 */
	@Transactional
	public void insertCon (Map<String, Object> map){
		makePlanMapper.insertCon(map);
	}
	@Transactional
	public void insertCust (Customer customer){
		makePlanMapper.insertCust(customer);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	@Transactional
	public SalesChance getSalesChanceById(Integer id){
		SalesChance salesChance = makePlanMapper.getSaleChanceById(id);
		Set<SalesPlan> listSalesPlanById = makePlanMapper.getListSalesPlanById(id);
		
		salesChance.setSalesPlans(listSalesPlanById);
		
		return salesChance;
	}
	
	/**
	 * 
	 * @param map
	 */
	@Transactional
	public void insertSalesPlan(Map<String,Object> map){
		makePlanMapper.insert(map);
	}


	@Transactional
	public String delete(int id) {
		return Integer.toString(makePlanMapper.delete(id));
	}


	@Transactional
	public String updateById(String todo, int id) {
		return Integer.toString(makePlanMapper.updateById(todo,id));
	}

}
