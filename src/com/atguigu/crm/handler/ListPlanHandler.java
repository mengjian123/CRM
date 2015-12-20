package com.atguigu.crm.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.MakePlanService;
import com.atguigu.crm.service.SaleChanceService;
import com.atuigu.crm.entity.SalesChance;
import com.atuigu.crm.entity.User;

@RequestMapping(value = "/plan")
@Controller
public class ListPlanHandler {

	@Autowired
	private SaleChanceService saleChanceService;
	
	@Autowired
	private MakePlanService makePlanService;

	@RequestMapping(value = "/chance/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String getPagedPlanList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map, HttpServletRequest request,
			HttpSession session) {
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}

		// 1. 获取查询条件的请求参数的 Map. 具体得到的 Map 的键是去除了 filter_ 的参数名
		Map<String, Object> params = WebUtils.getParametersStartingWith(
				request, "filter_");
		
		
		// 2. 调用 Service 方法得到 Page 对象.
		User user=(User) session.getAttribute("user");
		Long loginUserId=user.getId();
		
		Page<SalesChance> page = saleChanceService.getPagedPlanList(pageNo, 4,
				params,  loginUserId);
	

		// 3. 把 Page 放到 request 中
		map.put("page", page);

		// 4. 把 Map 在序列化为一个查询字符串传到页面上.
		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		map.put("queryString", queryString);

		return "plan/list";
	}

	/**
	 * 为上个方法配置的方法
	 * 
	 * @param params
	 * @param prefix
	 * @return
	 */
	public static String encodeParameterStringWithPrefix(
			Map<String, Object> params, String prefix) {
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
			queryStringBuilder.append(prefix).append(entry.getKey())
					.append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	@RequestMapping(value = "/execute/{id}", method = RequestMethod.GET)
	public String executePlan(@PathVariable(value = "id") Integer id,
			Map<String, Object> map) {
		//System.out.println("--------------id" + id);
		//SalesChance salesChance = saleChanceService.getSalesChanceById(id);
		SalesChance chanceById = makePlanService.getSalesChanceById(id);
		
		map.put("chance", chanceById);
		return "plan/exec";
	}
	
	
}
