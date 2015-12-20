package com.atguigu.crm.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ActivityCRUDService;
import com.atuigu.crm.entity.Contact;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerActivity;

@RequestMapping(value="/activity")
@Controller
public class ActivityCRUDHandler {

	@Autowired
	private ActivityCRUDService activityCRUDService;
	
	
	/**
	 * 显示页面list
	 * 参数里的map应该是放在前台的
	 */
	@RequestMapping(value="/list/{id}",method={RequestMethod.GET,RequestMethod.POST})
	public String getList(@RequestParam(value="pageNo",required=false) String pageNoStr,Map<String,Object> map,
			HttpServletRequest request,@PathVariable("id") Integer customerid){
		int pageNo=1;
		try{
			pageNo = Integer.parseInt(pageNoStr);
		}catch(Exception e){}
		//将customerid放进去
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", customerid);
		System.out.println("不错哈哈");
		//2. 调用 Service 方法得到 Page 对象.
		Page<CustomerActivity> page = activityCRUDService.getPage(pageNo,4,params);
		
		Customer customerById = activityCRUDService.getCustomerById(customerid);
		//3. 把 Page 放到 request 中
		map.put("page", page);   
		map.put("customer", customerById);
		return "activity/list";
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
		
		map.put("activity", new CustomerActivity());
		
		return "activity/input";
	}
	
	
	/**
	 * 在存储页面的界面
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(@RequestParam(value="date",required=false) String date,
			@RequestParam(value="place",required=false) String place,
			@RequestParam(value="title",required=false) String title,
			@RequestParam(value="description",required=false) String description,
			@RequestParam(value="customerid",required=false) String customerid){
		
		activityCRUDService.save(date,place,title,description,customerid);

		System.out.println(customerid+"++++++++++++++++");
		return "redirect:/activity/list/"+customerid;
	}
	
	/**
	 * 删除对象
	 * @param id
	 * @param customerid
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="customerid",required=false) Integer customerid){
		activityCRUDService.delete(id);
		//System.out.println(customerid);
		//早知道这么玩上面都不用改
		return "redirect:/activity/list/"+customerid;
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String toUpdate(@RequestParam(value="customerid",required=false) Integer customerid,
			Map<String,Object> map,
			@RequestParam(value="id",required=false) Integer contactid){
		
		map.put("customerid",customerid);
		
		//在数据库中查  根据id查contact
		CustomerActivity ca = activityCRUDService.getActivityById(contactid);

		map.put("activity", ca);
		
		return "activity/input";
	}


	/**
	 * 实际修改的方法
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.PUT)
	public String update(@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="customerid",required=false) Integer customerid,CustomerActivity act){
		
		activityCRUDService.update(act);	
		
		System.out.println(customerid+"------------------");
		
		return "redirect:/activity/list/"+customerid;
	}
	
	
	
	
	/**
	 * 为修改方法做铺垫
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getActivity(@RequestParam(value="id",required=false)  Integer id,Map<String,Object> map){
		if(id!=null){
			map.put("act", activityCRUDService.getActivityById(id));
		}
	}
	
}
