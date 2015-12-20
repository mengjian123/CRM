package com.atguigu.crm.handler;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.service.MakePlanService;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.SalesChance;

@RequestMapping(value="/plan")
@Controller
public class MakePlanHandler {
	
	@Autowired
	private MakePlanService makePlanService;
	
	
	/**
	 * @method 查看
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String detail(@RequestParam("id") Integer id, Map<String,Object> map){
		System.out.println("detail" + id);
		SalesChance chance = makePlanService.getSalesChanceById(id);
		map.put("chance", chance);
		return "/plan/detail";
	}
	
	/**
	 * @method 开发失败
	 * Get
	 */
	@RequestMapping(value="/chance/stop",method=RequestMethod.PUT)
	public String stop(@RequestParam("id") Integer id){
		makePlanService.stop(id);
		return "redirect:/plan/chance/list";
	}	
	
	/**
	 * @method 开发成功
	 */
	@RequestMapping(value="/chance/finish",method=RequestMethod.PUT)
	public String finish(@RequestParam("id") Integer id){
		makePlanService.finish(id);
		
		SalesChance salesChance = makePlanService.getSalesChanceById(id);
		
		Customer customer = new Customer();
		customer.setName(salesChance.getCustName());
		customer.setNo(UUID.randomUUID().toString());
		customer.setState("正常");
		System.out.println(customer.getName()+ customer.getNo() + customer.getState());
		makePlanService.insertCust(customer);
		//===============================================
		
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("name", salesChance.getContact());
		map.put("tel", salesChance.getContactTel());
		map.put("customer_id", customer.getId());
		makePlanService.insertCon(map);
		
		return "redirect:/plan/chance/list";
	}
	
	/**
	 * 去制定计划的页面,回显信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/make/{id}",method=RequestMethod.GET)
	public String toMakePlan(@PathVariable(value="id") Integer id,Map<String,Object> map){
		//需要在后台查询salechance，回显
		SalesChance chanceById = makePlanService.getSalesChanceById(id);
		
		map.put("chance", chanceById);
		
		return "plan/make";
	}
	
	
	
	
	/**
	 * 新建计划项和日期
	 * Ajax操作
	 *  有瑕疵  得改
	 */
	@RequestMapping(value="/new-ajax",method=RequestMethod.POST)
	public SalesChance toNewPlan(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		String todo = request.getParameter("todo");
		String date = request.getParameter("date");
		
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("id", id);
		map.put("todo", todo);
		map.put("date", date);
		
		makePlanService.insertSalesPlan(map);
		
		return makePlanService.getSalesChanceById(id);
	}
	

	

	
		/**
	 * AJAX操作删除操作
	 */
	@RequestMapping(value="/delete-ajax",method=RequestMethod.POST)
	public String toDelete(HttpServletRequest request){
		String id = request.getParameter("id");
		
		//System.out.println(id);
		//System.out.println(makePlanService.delete(Integer.parseInt(id)));
		return makePlanService.delete(Integer.parseInt(id));

	}
	
	
		/**
	 * 修改操作
	 * @return
	 */
	@RequestMapping(value="/make-ajax",method=RequestMethod.POST)
	public String toUpdate(HttpServletRequest request){
		String id = request.getParameter("id");
		String todo = request.getParameter("todo");
		
		System.out.println(id+"-----"+todo);
		
		return makePlanService.updateById(todo,Integer.parseInt(id));
		
	}
}
