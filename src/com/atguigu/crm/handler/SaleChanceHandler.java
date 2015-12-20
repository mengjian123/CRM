package com.atguigu.crm.handler;

import java.util.Iterator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.SaleChanceService;
import com.atguigu.crm.service.UserService;
import com.atuigu.crm.entity.SalesChance;

@RequestMapping("/chance")
@Controller
public class SaleChanceHandler {
	
	@Autowired
	private SaleChanceService saleChanceService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String getList(@RequestParam(value="pageNo",required=false) String pageNoStr,Map<String,Object> map,
			HttpServletRequest request){
		int pageNo=1;
		try{
			pageNo = Integer.parseInt(pageNoStr);
		}catch(Exception e){}
	
		
		//1. 获取查询条件的请求参数的 Map. 具体得到的 Map 的键是去除了 filter_ 的参数名
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "filter_");
		System.out.println("---------------------"+params);
		
		//2. 调用 Service 方法得到 Page 对象.
		Page<SalesChance> page = saleChanceService.getPage(pageNo,4,params);
		
		//3. 把 Page 放到 request 中
		map.put("page", page);   
		
		//4. 把 Map 在序列化为一个查询字符串传到页面上.
		String queryString = encodeParameterStringWithPrefix(params, "filter_");
		map.put("queryString", queryString);
		
		return "chance/list";
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
	/**
	 * 实际存储的功能
	 * @param salesChance
	 * @param attributes
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String save(SalesChance salesChance,RedirectAttributes attributes){
		
		saleChanceService.save(salesChance);
		
		attributes.addFlashAttribute("message", "添加成功!");
		return "redirect:/chance/list";
	}
	
	/**
	 * 去存储对象界面的功能
	 * @param map
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String toSave(Map<String,Object> map){
		
		map.put("chance", new SalesChance());
		
		return "chance/input";
	}
	
	
	/**
	 * 回显对象属性
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String toUpdate(@PathVariable(value="id") String id,Map<String,Object> map){
		
		map.put("chance", saleChanceService.getSalesChanceById(Integer.parseInt(id)));
		
		return "chance/input";
	}
	
	/**
	 * 实际修改的方法
	 * @param salesChance
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(SalesChance salesChance){
		saleChanceService.update(salesChance);
		
		return "redirect:/chance/list";
	}
//	
//	
	/**
	 * 删除的方法
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable(value="id") String id){
		int id1= 0;
		try{
			id1=Integer.parseInt(id);
		}catch(Exception e){}
		
		saleChanceService.delete(id1);
		return "redirect:/chance/list";
	}
	
	
	/**
	 * 每个方法都调用这个
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getSalesChanceById1(@RequestParam(value="id",required=false) Integer id,Map<String,Object> map){
		//在添加的时候，先调用这个方法，添加的id为空，查不出来对象，如果进入这个方法的话，那么就会产生空对象，出现空指针异常的错误
		if(id!=null){
			map.put("salesChance", saleChanceService.getSalesChanceById(id));
		}
	}
	/**
	 * 前往指派页面
	 * @param id  根据ID获取用户
	 * @param map 获取所有的用户,进行指派
	 * @return 
	 */
	@RequestMapping(value="/dispatch/{id}", method=RequestMethod.GET)
	public String dispatch(@PathVariable("id")Integer id,Map<String,Object> map){
		SalesChance salesChance = saleChanceService.getSalesChanceById(id);
		map.put("chance", salesChance);
		map.put("users", userService.getAll());
		return "chance/dispatch";
	}
	
	/**
	 * 进行指派功能
	 * @param salesChance
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/dispatch/{id}",method=RequestMethod.PUT)
	public String dispatch(SalesChance salesChance){
		//将status修改为2(执行中)
		//修改指派时间
		//修改指派人
		salesChance.setStatus(2);
		System.out.println(".................................");
		saleChanceService.dispatch(salesChance);
		return "redirect:/chance/list";
	}
	
}
