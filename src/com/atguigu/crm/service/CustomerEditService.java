package com.atguigu.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.daos.CustomerEditMapper;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

@Service
public class CustomerEditService {

	@Autowired
	private CustomerEditMapper customerEditMapper;

	@Transactional
	public Customer getCustomerById(Integer id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		Customer customer = customerEditMapper.getCustomerInfoById(map);
		return customer;
	}

	@Transactional
	public List<String> getCustomerLevels() {
		//return customerEditMapper.getCustomerLevels();
		String str = "Not Known";
		List<String> originLevels = customerEditMapper.getCustomerLevels();
		List<String> modLevels = new ArrayList<>();
		for (String level : originLevels) {
			if (level != null) {
				modLevels.add(level);
			}
			if (level == null) {
				modLevels.add(str);
			}
		}
		return modLevels;
	}

	@Transactional
	public List<String> getRegions() {
		String str = "no region deployed";
		List<String> orginRegions = customerEditMapper.getRegions();
		List<String> modRegions = new ArrayList<>();
		for (String region : orginRegions) {
			if (region != null) {
				modRegions.add(region);
			}
			if (region == null) {
				modRegions.add(str);
			}
		}
		return modRegions;
	}

	@Transactional
	public List<Contact> getManagers() {
		Contact newCont = new Contact();
		List<Contact> orginManagers = customerEditMapper.getManager();
		// System.out.println("----------managers----------"+orginManagers);
		return orginManagers;
	}

	@Transactional
	public List<String> getSatisfy() {
		String str = "Not Known";
		List<String> orginSatisfy = customerEditMapper.getSatisfy();
		List<String> modSatisfy = new ArrayList<>();
		for (String satisfy : orginSatisfy) {
			if (satisfy != null) {
				modSatisfy.add(satisfy);
			}
			if (satisfy == null) {
				modSatisfy.add(str);
			}
		}
		return modSatisfy;
	}

	@Transactional
	public List<String> getCredits() {
		String str = "Not Known";
		List<String> orginCredit = customerEditMapper.getCredit();
		List<String> modCredit = new ArrayList<>();
		for (String credit : orginCredit) {
			if (credit != null) {
				modCredit.add(credit);
			}
			if (credit == null) {
				modCredit.add(str);
			}
		}
		return modCredit;
	}

	@Transactional
	public void editCustomer(Map map) {
		customerEditMapper.updateCustomerById(map);
	}
}
