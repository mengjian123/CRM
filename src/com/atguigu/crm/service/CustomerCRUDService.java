package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.CustomerCRUDMapper;
import com.atguigu.crm.orm.Page;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

@Service
public class CustomerCRUDService {

	@Autowired
	private CustomerCRUDMapper  customerCRUDMapper;
	
	@Transactional
	public Page<Contact> getPage(int pageNo,int pageSize,Map<String,Object> params){
		
		Page<Contact> page =new Page<>();
		//用前台传入的pageNo替换属性里 的pageNo
		page.setPageNo(pageNo);

		//总记录数
		long totalAmount = customerCRUDMapper.getTotalAmount(params);
		page.setTotalElements(totalAmount);
		//System.out.println(totalAmount);
		
		int fromIndex = (pageNo-1)*page.getPageSize()+1;
		int endIndex=fromIndex + page.getPageSize();
		
		params.put("fromIndex", fromIndex);
		params.put("endIndex", endIndex);
		
		List<Contact> content = customerCRUDMapper.getContont(params);
		page.setContent(content);

		return page;
	}
	
	@Transactional
	public Customer getCustomerById(Integer id){
		return customerCRUDMapper.getCustomerById(id);
	}

	@Transactional
	public void save(String name, String sex, String position, int tel,
			int mobile, String memo,int customerid) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("name", name);
		map.put("sex", sex);
		map.put("position", position);
		map.put("tel", tel);
		map.put("mobile", mobile);
		map.put("memo", memo);
		map.put("customerid", customerid);
		
		customerCRUDMapper.save(map);
	}

	@Transactional
	public void delete(Integer id) {
		customerCRUDMapper.delete(id);
	}

	/**
	 * 根据id在数据库中查
	 * @param contactid
	 * @return
	 */
	@Transactional
	public Contact getContactById(Integer contactid) {
		return customerCRUDMapper.getContactById(contactid);
	}

	/**
	 * 
	 * @param con
	 */
	@Transactional
	public void update(Contact con) {
		customerCRUDMapper.update(con);
	}
}
