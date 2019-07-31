package patentweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.patent.pojo.user;
import cn.edu.patent.service.userService;
import lombok.extern.log4j.Log4j2;

/**
 * @author:JXH
 * @date:2019年6月25日-下午3:47:05
 * -登录或者注册.
 */
@Controller
@Log4j2
public class LoginAndRegisterController {
	@Autowired
	private userService userService;
	@RequestMapping("login")
	public String login(@RequestParam String name,@RequestParam String password,HttpServletRequest request,HttpSession session) {
		log.log(Level.forName("work",50),"登录用户:"+name+" 密码:"+password);
		return userService.login(name, password, session, request);
		
	}
	@RequestMapping("login/page")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("register")
	public String register(@RequestParam String name,@RequestParam String password,HttpServletRequest request) {
		log.log(Level.forName("work",50),"注册用户:"+name+" 密码:"+password);
		return userService.register(name, password, request);
	}
	
	
	
}
