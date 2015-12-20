package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.service.CustomerEditService;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

@Controller
@RequestMapping(value="/customer")
public class CustomerEditHandler {
	@Autowired
	private CustomerEditService customerEditService;
	
	@RequestMapping(value="/{id}" , method=RequestMethod.GET)
	public String toEditPage(@PathVariable("id")Integer id, Map<String ,Object> map ){
		//System.out.println("-------------------this is id------"+id);
		Customer customer = customerEditService.getCustomerById(id);
		map.put("customer", customer);
		List<String> levels = customerEditService.getCustomerLevels();
		//System.out.println("----------levle========="+levels);
		map.put("levels", levels);
		List<String >regions =customerEditService.getRegions();
		//System.out.println("----------regions========="+regions);
		map.put("regions", regions);
		List<Contact >contacts =customerEditService.getManagers();
		//System.out.println("manager------------------"+managers);
		map.put("contacts", contacts);
		List<String> satisfies=customerEditService.getSatisfy();
		//System.out.println("satisfies================="+satisfies);
		map.put("satisfies", satisfies);
		List<String>credits=customerEditService.getCredits();
		//System.out.println("----credits----"+credits);
		map.put("credits", credits);
		return "customer/input";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String edit(HttpServletRequest request ){
		Map<String, String[]> requestMap = request.getParameterMap();
		Map<String , String >map=new HashMap<String, String>();
		for(Entry<String, String[]> entry: requestMap.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue()[0];
			//System.out.println(key+"==="+value);
			map.put(key, value);
		}
		for(Map.Entry<String, String> entry: map.entrySet()){
			System.out.println(entry.getKey()+"==="+entry.getValue());
		}
		customerEditService.editCustomer(map);
		//System.out.println("into edit");
		return "redirect:/customer/list";
	}
	@ModelAttribute
	public void getCustomer(@RequestParam(value ="id", required=false) Integer id ){
		if(id!=null){
			Customer customer = customerEditService.getCustomerById(id);
		}
	}
	
	
}
