package patentweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.edu.patent.pojo.user;
import cn.edu.patent.service.userService;

/**
 * @author:JXH
 * @date:2019年6月28日-下午4:49:21
 * -后台管理.
 */
@Controller
public class adminController {
	@Autowired
	private userService userService;
	@RequestMapping("admin")
	public ModelAndView admin() {
		ModelAndView modelAndView=new ModelAndView("admin");
		//--人工标注分类json.
		String echartsrg=userService.echartsRg();
		modelAndView.addObject("echartsrg", echartsrg);
		modelAndView.addObject("jsTree", userService.JsTree());
		modelAndView.addObject("_view", userService.generalView());
		return modelAndView;
	}
    
	@RequestMapping("admin/insert")
	@ResponseBody
	public String adminInsert(@RequestParam("name")String name,@RequestParam("pass")String pass) {
		return userService.registerRoot(name, pass);
	}
	@ResponseBody
	@RequestMapping("admin/delete")
	public String adminDelete(@RequestParam("name")String name,@RequestParam("pass")String pass) {
		 userService.deleteRoot(name, pass);
		 return "1";
	}
	@ResponseBody
	@RequestMapping("admin/update")
	public String adminUpdate(@RequestParam("name")String name,@RequestParam("pass")String pass,HttpSession session) {
		 user user=(cn.edu.patent.pojo.user) session.getAttribute("user");
		 userService.updateRoot(name, pass,user.getName(),user.getPassword());
		 return "1";
	}
	
	@ResponseBody
	@RequestMapping("admin/user/table")
	public JSONObject UserTable(
			@RequestParam("page")int page,
			@RequestParam("limit")int limit
			) {
		return userService.find_table_user(page,limit);
	}
	@ResponseBody
	@RequestMapping("admin/root/table")
	public JSONObject RootTable() {
		return userService.find_table_root();
	}
	@RequestMapping("admin/out")
	public String outLogin(HttpSession session) {
		session.removeAttribute("user");
		return "login";
	}
	@ResponseBody
	@RequestMapping("admin/user/shiro")
	public String UserShiro(@RequestParam("id") Integer id) {
		 userService.shiro(id);
		 return "1";
	}

}
