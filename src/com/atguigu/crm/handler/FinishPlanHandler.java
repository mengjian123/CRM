package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.crm.service.FinishChanceService;
import com.atguigu.crm.service.SaleChanceService;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;

@RequestMapping(value = "/chance")
@Controller
public class FinishPlanHandler {


	@Autowired
	private FinishChanceService finishChanceService;
	@Autowired
	private SaleChanceService saleChanceService;
	@RequestMapping(value="/finish/{id}")
	public String finish(@PathVariable("id")Integer id){
		
		finishChanceService.finish(id);
		
		System.out.println("-----------------id"+id);
		SalesChance salesChance = saleChanceService.getSalesChanceById(id);
		salesChance.setStatus(3);
		/**
		 *  向 customers 和 contacts 数据表中各插入一条记录。
		 *  Cusomers 数据表中插入 3 个字段：name，no（随机字符串） 和 state（正常）
		 *  .向 contacts 数据表也插入 3 个字段：name、tel、customer_id
		 * **/
		Customer customer = new Customer();
		customer.setName(salesChance.getCustName());
		customer.setNo(UUID.randomUUID().toString());
		customer.setState("正常");
		System.out.println("********************************"+customer.getName() + customer.getNo() + customer.getState());
		finishChanceService.saveCustomer(customer);
		//===============================================
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("name", salesChance.getContact());
		map.put("tel", salesChance.getContactTel());
		map.put("customer_id", customer.getId());
		finishChanceService.saveContact(map);
		
		return "redirect:/plan/chance/list";
	}
	
}
