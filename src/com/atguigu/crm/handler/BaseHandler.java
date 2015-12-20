package com.atguigu.crm.handler;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.service.jpa.BaseService;
import com.atuigu.crm.entity.Product;

public class BaseHandler<T,PK extends Serializable> {
	
	@Autowired
	private BaseService<T, PK> baseService;
	
	public String tolist(String pageNoStr,HttpServletRequest request){
		//1.获取剔除前面的search的后面字符串
		Map<String, Object> map = WebUtils.getParametersStartingWith(request, "search_");
		
		Integer pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}

		//2.调用方法
		Page<T> page =baseService.getPage(pageNo-1,5,map);
		
		//3.放到request中
		request.setAttribute("page", page);
		
		//4.弄成字符串 传给前台
		String queryString = SaleChanceHandler.encodeParameterStringWithPrefix(map, "search_");
		request.setAttribute("searchParams", queryString);
		
		
		return null;
	}

	public void tosave(Product pro) {
		baseService.save(pro);
	}

	
	public Product getInstance2(PK id) {
		// TODO Auto-generated method stub
		return baseService.get(id);
	}
	
	public void update1(Product pro){
		baseService.update(pro);
	}
	
	public void delete1(Product pro){
		baseService.delete(pro);
	}


}
