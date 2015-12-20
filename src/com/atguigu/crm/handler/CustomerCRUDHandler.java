package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerCRUDService;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;

@RequestMapping(value="/contact")
@Controller
public class CustomerCRUDHandler {
	
	
	@Autowired
	private CustomerCRUDService customerCRUDService;
	/**
	 * 带分页的列表
	 * @return
	 */
	@RequestMapping(value="/list/{id}",method={RequestMethod.GET,RequestMethod.POST})
	public String getList(@RequestParam(value="pageNo",required=false) String pageNoStr,Map<String,Object> map,
			HttpServletRequest request,@PathVariable("id") Integer id){
		int pageNo=1;
		try{
			pageNo = Integer.parseInt(pageNoStr);
		}catch(Exception e){}
		//将customerid放进去
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		//2. 调用 Service 方法得到 Page 对象.
		Page<Contact> page = customerCRUDService.getPage(pageNo,4,params);
		Customer customerById = customerCRUDService.getCustomerById(id);
		//3. 把 Page 放到 request 中
		map.put("page", page);   
		map.put("customer", customerById);
		return "contact/list";
	}
	
	
	
	/**
	 * 新建转向input界面
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toSave(@RequestParam(value="customerid",required=false) Integer customerid,
			Map<String,Object> map,
			@RequestParam(value="id",required=false) Integer contactid){
		
		map.put("customerid",customerid);

		map.put("contactid", contactid);
		
		map.put("contact", new Contact());
		
		return "contact/input";
	}
	
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String toUpdate(@RequestParam(value="customerid",required=false) Integer customerid,
			Map<String,Object> map,
			@RequestParam(value="id",required=false) Integer contactid){
		
		map.put("customerid",customerid);
		
		//在数据库中查  根据id查contact
		Contact con = customerCRUDService.getContactById(contactid);

		map.put("contact", con);
		return "contact/input";
	}
	
	
	/**
	 * 在存储页面的界面
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(@RequestParam(value="name",required=false) String name,
			@RequestParam(value="sex",required=false) String sex,
			@RequestParam(value="position",required=false) String position,
			@RequestParam(value="tel",required=false) String tel,
			@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="memo",required=false) String memo,
			@RequestParam(value="customerid",required=false) String customerid){
		customerCRUDService.save(name,sex,position,Integer.parseInt(tel),
				Integer.parseInt(mobile),memo,Integer.parseInt(customerid));
//		System.out.println(customerid+"--------------");
//		System.out.println(memo);
//		System.out.println(mobile);
//		System.out.println(tel);
//		System.out.println(position);
//		System.out.println(sex);
//		System.out.println(name);
		System.out.println(customerid+"++++++++++++++++");
		return "redirect:/contact/list/"+customerid;
	}
	
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="customerid",required=false) Integer customerid){
		customerCRUDService.delete(id);
		//System.out.println(customerid);
		//早知道这么玩上面都不用改
		return "redirect:/contact/list/"+customerid;
	}
	
	/**
	 * 实际修改的方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String update(@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="customerid",required=false) Integer customerid,Contact con){
		
		customerCRUDService.update(con);
		
		System.out.println(customerid+"------------------");
		
		return "redirect:/contact/list/"+customerid;
	}
	
	
	/**
	 * 为修改方法做铺垫
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getContact(@RequestParam(value="id",required=false)  Integer id,Map<String,Object> map){
		if(id!=null){
			map.put("con", customerCRUDService.getContactById(id));
		}
	}
	
}
