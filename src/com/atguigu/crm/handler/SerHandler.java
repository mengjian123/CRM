package com.atguigu.crm.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SerService;
import com.atuigu.crm.entity.Customer;
import com.atuigu.crm.entity.CustomerService;
import com.atuigu.crm.entity.User;

@RequestMapping(value="/service")
@Controller
public class SerHandler {
	
	@Autowired
	private SerService serService;
	
	
	/**
	 *去往input界面 新建服务的页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/input",method=RequestMethod.GET)
	public String toInput(Map<String,Object> map){
		
		List<String> customerService = serService.getCustomerService();
		List<Customer> customer = serService.getCustomer();
		
		map.put("serviceTypes", customerService);
		map.put("customers", customer);
		
		return "service/input";
	}
	
	
	/**
	 * 实际保存的方法
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String save(@RequestParam(value="serviceType",required=false) String serviceType,
			@RequestParam(value="serviceTitle",required=false) String serviceTitle,
			@RequestParam(value="customer.id",required=false) String customerid,
			@RequestParam(value="serviceState",required=false) String serviceState,
			@RequestParam(value="createDate",required=false) String createDate,
			@RequestParam(value="serviceRequest",required=false) String serviceRequest){
		
		serService.save(serviceType,serviceTitle,customerid,serviceState,createDate,serviceRequest);
		
		return "redirect:/service/input";
	}
	
	
	/**
	 * 显示分配页面 /allot/list
	 */
	@RequestMapping(value="/allot/tolist",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@RequestParam(value="pageNo",required=false) String pageNoStr
			,Map<String,Object> map,HttpServletRequest request){
		//需要显示带查询条件的分页
		int pageNo=1;
		try{
			pageNo = Integer.parseInt(pageNoStr);
		}catch(Exception e){}
		
		//1. 获取查询条件的请求参数的 Map. 具体得到的 Map 的键是去除了 filter_ 的参数名
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		
		//2. 调用 Service 方法得到 Page 对象.
		Page<CustomerService> page = serService.getPage(pageNo,4,params);
		List<User> userList = serService.getUserList();
		
		//3. 把 Page 放到 request 中
		map.put("page", page);   
		map.put("users", userList);
		
		//4. 把 Map 在序列化为一个查询字符串传到页面上.
		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		map.put("queryString", queryString);
		
		
		return "service/allot/list";
	}
	
	/**
	 * 为上个方法配置的方法
	 * @param params
	 * @param prefix
	 * @return
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		if ((params == null) || (params.size() == 0)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}
	
}
