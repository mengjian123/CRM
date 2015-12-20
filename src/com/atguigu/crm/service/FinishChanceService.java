package com.atguigu.crm.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.ContactRepository;
import com.atguigu.crm.daos.CustomerRepository;
import com.atguigu.crm.daos.SaleChanceMapper;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;

@Service
public class FinishChanceService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private SaleChanceMapper saleChanceMapper;
	/**
	 * 开发成功相关
	 * @param id
	 */
	@Transactional
	public void saveCustomer(Customer customer){
		customerRepository.save(customer);
	}

	@Transactional
	public void saveContact(Map<String, Object>map){
		contactRepository.save(map);
	}
	
	@Transactional
	public void finish(Integer id) {
		saleChanceMapper.finish(id);
	}
}
