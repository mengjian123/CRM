package com.atguigu.crm.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.Request;

import com.atguigu.crm.service.CustomerDeleteService;
import com.atuigu.crm.entity.Customer;

@RequestMapping(value="/customer")
@Controller
public class CustomerDeleteHandler {

	@Autowired
	private CustomerDeleteService customerDeleteService;
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable("id")Integer id){
		
		Customer customer = customerDeleteService.get(id);
		System.out.println("******************"+ customer);
		
		customer.setState("删除");
		customerDeleteService.save(customer);
		return "redirect:/customer/list";
	}
	
}
