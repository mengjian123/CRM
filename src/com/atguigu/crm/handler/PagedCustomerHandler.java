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
import com.atguigu.crm.service.PagedCustomerService;
import com.atuigu.crm.entity.Customer;

@Controller
@RequestMapping(value="/customer")
public class PagedCustomerHandler {

	@Autowired
	private PagedCustomerService pagedCustomerService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String getList(@RequestParam(value="pageNo",required=false ,defaultValue="1") String pageNoStr,Map<String,Object> map,
			HttpServletRequest request){
		int pageNo=1;
		try{
			pageNo = Integer.parseInt(pageNoStr);
		}catch(Exception e){}
	
		
		//1. 获取查询条件的请求参数的 Map. 具体得到的 Map 的键是去除了 filter_ 的参数名
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		
		//2. 调用 Service 方法得到 Page 对象.
		Page<Customer> page = pagedCustomerService.getPage(pageNo, 4, params);
		
		//3. 把 Page 放到 request 中
		map.put("page", page);   
		
		//4. 把 Map 在序列化为一个查询字符串传到页面上.
		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		map.put("queryString", queryString);
		
		//-----------------------SET UP REGIONS-------------------------
		List<String> regions = pagedCustomerService.getRegions();
		map.put("regions", regions);
		
		//-----------------------SET UP LEVELS-----------------------
		List<String> levels = pagedCustomerService.getCustomerLevel();
		map.put("levels", levels);
		//--------------------------BULLSHIT---------------------------
		
		
		return "customer/list";
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
