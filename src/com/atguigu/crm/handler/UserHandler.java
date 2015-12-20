package com.atguigu.crm.handler;



import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.service.UserService;
import com.atuigu.crm.entity.User;

@RequestMapping("/user")
@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam(value="username",required=false) String name,
			@RequestParam(value="password", required=false) String password,
			HttpSession session, RedirectAttributes attributes, Locale locale){
		//1. 可以手工完成对 username 和 password 的简单验证
		
		//2. 调用 Service 完成登录操作
		User user = userService.login(name, password);
		if(user == null){
			attributes.addFlashAttribute("message", messageSource.getMessage("error.user.login", 
					null, locale));
			return "redirect:/index";
		}
		
		session.setAttribute("user", user);
		return "home/success";
	}
	
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		
		session.removeAttribute("user");
		
		return "redirect:/index";
	}
}
