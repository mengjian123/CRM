package com.atguigu.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.CustomerDeleteRepository;
import com.atuigu.crm.entity.Customer;

@Transactional
@Service
public class CustomerDeleteService {

	@Autowired
	private CustomerDeleteRepository customerDeleteRepository;

	
	public Customer get(Integer id) {
		return customerDeleteRepository.findOne(id);
	}

	public void save(Customer customer) {
		customerDeleteRepository.update(customer);
	}


}
