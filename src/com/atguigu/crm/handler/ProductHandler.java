package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.service.jpa.ProductService;
import com.atuigu.crm.entity.Product;

@RequestMapping(value="/product")
@Controller
public class ProductHandler extends BaseHandler<Product, Long> {

	@Autowired
	private ProductService productService;
	
	/**
	 * 显示界面
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String tolist(@RequestParam(value="page",defaultValue="1",required=false) 
	String pageNoStr,HttpServletRequest request){

		super.tolist(pageNoStr, request);
		
		return "product/list";
	}
	
	
	/**
	 *去增加页面的方法
	 */
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toSave(Map<String,Object> map){
		map.put("product", new Product());
		
		return "product/input";
	}
	
	/**
	 * 实际操作增加的方法
	 * @return 
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Product pro){
		
		super.tosave(pro);
		
		return "redirect:/product/list";
	}
	
	/**
	 * 去修改界面的方法
	 */
	@RequestMapping(value="/toupdate",method=RequestMethod.GET)
	public String toUpdate(@RequestParam("id") Long id,Map<String,Object> map){
		Product instance2 = super.getInstance2(id);
		
		map.put("product", instance2);
		
		return "product/input";
	}
	
	/**
	 * 实际修改的方法
	 * @return
	 */
	@RequestMapping(value="/save",method=RequestMethod.PUT)
	public String Update(Product pro){
		super.update1(pro);
		
		return "redirect:/product/list";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(Product pro){
		
		super.delete1(pro);
		return "redirect:/product/list";
	}
	
	
	/**
	 * modelAttr
	 */
	@ModelAttribute
	public void getInstance(Long id,Map<String,Object> map){
		if(id != null){
			map.put("pro", super.getInstance2(id));
			//修改这两个哪个回显都行，必须要两个对象就好
		}
	}
}
